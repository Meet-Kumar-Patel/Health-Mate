package com.example.project13application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project13application.databinding.ActivitySignUpBinding
import com.example.project13application.utilities.FormValidator
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val signupButton: Button = binding.signupButton
        val signinText: TextView = binding.navigateToSignInButton

        signupButton.setOnClickListener {
            val emailText = binding.editTextEmailAddress.text.toString()
            val passwordText= binding.editTextPassword.text.toString()
            val confirmPasswordText = binding.editTextConfirmPassword.text.toString()

            if (!FormValidator.isValidEmailAddress(emailText)){
                binding.editTextEmailAddress.error = "Enter a valid email"
            }

            if (!FormValidator.isValidatePassword(passwordText)) {
                binding.editTextPassword.error = "Minimum password requirements: 1 uppercase letter, 1 number, 8 characters"
            }

            if (!FormValidator.isPasswordMatched(passwordText, confirmPasswordText)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }

            if (FormValidator.isFormComplete(emailText, passwordText, confirmPasswordText)){
                auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("com.example.project13application.SignUpActivity", "createUserWithEmail:success")
                            startActivity(Intent(this@SignUpActivity, TestActivity::class.java))
                            finish()
                        } else {
                            Log.w("com.example.project13application.SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        signinText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
