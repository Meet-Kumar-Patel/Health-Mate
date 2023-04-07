package com.example.project13application //<- New Change

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project13application.ui.models.Subscriber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<TextView>(R.id.navigateToSignUpButton) //<- New change

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginUser(email, password)
        }

        //Navigate to sign up if user does not have an account
        signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "Login was successful, proceed to fetch subscriber details")
                    fetchSubscriberDetails()
                } else {
                    Log.e("Login", "Login failed: ${task.exception?.message}")
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun fetchSubscriberDetails() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val subscribersRef = FirebaseDatabase.getInstance().getReference("subscribers")
            subscribersRef.orderByChild("userId").equalTo(currentUser.uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (data in snapshot.children) {
                            val subscriber = data.getValue(Subscriber::class.java)
                            if (subscriber != null) {
                                // You have the subscriber object, do whatever you need with it
                                // For example, navigate to the main activity or fetch patient details
                                startActivity(Intent(this@LoginActivity, TestActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Subscriber not found.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Login", "Failed to fetch subscriber details: ${error.message}")
                }
            })
        }
    }

}
