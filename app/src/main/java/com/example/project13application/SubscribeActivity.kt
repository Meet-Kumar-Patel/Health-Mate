package com.example.project13application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.project13application.databinding.ActivitySubscribeActivityBinding
import com.example.project13application.ui.models.Subscriber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SubscribeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySubscribeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
    }

    /**
     * UI Fork - Page displayed only if user is a family member and they are currently not
     * subscribed to any updates
     */
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val subscriberRef = Firebase.database.reference.child("subscribers").child(currentUser.uid)
            subscriberRef.get().addOnSuccessListener { snapshot ->
                val subscriber = snapshot.getValue(Subscriber::class.java)
                if (subscriber?.subscriptionCode?.isEmpty() == false) {
                    startActivity(Intent(this@SubscribeActivity, DetailPatientActivity::class.java))
                }
            }.addOnFailureListener { error ->
                Log.e("firebase", "Error getting subscriber data", error)
            }
        }
    }
}


