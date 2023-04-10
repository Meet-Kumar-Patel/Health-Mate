package com.example.project13application

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        val userToBeUpdated = intent.getStringExtra("userToBeUpdated").toString()
        val currentUser = intent.getStringExtra("currentUser").toString()
        val fullName = intent.getStringExtra("userNameToBeUpdated").toString()

        val userToBeUpdatedTV = findViewById<TextView>(R.id.addingNotesFor)

        userToBeUpdatedTV.setText(fullName)

        val currentUserTV = findViewById<TextView>(R.id.currentUserAddNotes)
        currentUserTV.setText(currentUser)

        val addButton = findViewById<Button>(R.id.addNotesButton)
        addButton.setOnClickListener {
            val addNotesML = findViewById<EditText>(R.id.addNotesML)
            val content = addNotesML.text.toString()

            val todayDate = "2023-04-09"
            val d : Diary = Diary(currentUser, todayDate, content)

            //firebase initial
            val database = FirebaseDatabase.getInstance().getReference("patients")
            database.child("$userToBeUpdated/diary entry/").push().setValue(d)
            Toast.makeText(this, "Add Successful", Toast.LENGTH_SHORT).show()

            val toDetails = Intent(this, DetailPatientActivity::class.java)
            toDetails.putExtra("key", userToBeUpdated)
            toDetails.putExtra("user", currentUser)
            toDetails.putExtra("userType", "CAREGIVER")
            this.startActivity(toDetails)

        }
    }
}