package com.example.project13application

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.project13application.databinding.ActivityTestBinding
import com.example.project13application.ui.models.Diary
import com.example.project13application.ui.models.Patient
import com.example.project13application.ui.models.Subscriber
import com.example.project13application.ui.models.SubscriberType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*

class TestActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityTestBinding
    private var selectedSubscriberType: SubscriberType = SubscriberType.FAMILY_MEMBER

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val patientFirstName = findViewById<EditText>(R.id.patientFirstName)
        val patientLastName = findViewById<EditText>(R.id.patientLastName)
        val diaryContent = findViewById<EditText>(R.id.diaryContent)
        val subscriberUsername = findViewById<EditText>(R.id.subscriberUsername)
        val subscriberTypeSpinner = findViewById<Spinner>(R.id.subscriberTypeSpinner)
        val addDataButton = findViewById<Button>(R.id.addDataButton)

        // Set up the spinner
        val subscriberTypes = arrayOf("Family Member", "Caregiver")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subscriberTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subscriberTypeSpinner.adapter = adapter

        subscriberTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSubscriberType = if (position == 0) SubscriberType.FAMILY_MEMBER else SubscriberType.CAREGIVER
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedSubscriberType = SubscriberType.FAMILY_MEMBER
            }
        }

        // Display today's date in layout
        val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
        binding.diaryDate.text = getString(R.string.diary_date, today.toString())

        addDataButton.setOnClickListener {

            val firstName = patientFirstName.text.toString()
            val lastName = patientLastName.text.toString()

            val content = diaryContent.text.toString()
            val username = subscriberUsername.text.toString()
            val canEdit = selectedSubscriberType == SubscriberType.CAREGIVER

            // Create objects
            val patient = Patient(id = "", username = "", firstName = firstName, lastName = lastName)
            val diaryEntry = Diary.create(id = "", date = today, content = content)
            val subscriber = Subscriber(id = "", username = username, type = selectedSubscriberType, canEdit = canEdit)


            // Get database reference
            val database = FirebaseDatabase.getInstance()

            val patientRef = database.getReference("patients")

            // Add patient to Firebase, Firebase auto-generated IDs
            val patientId = patientRef.push().key ?: ""
            patientRef.child(patientId).setValue(patient)

            // diary and subscriber are under the patient
            val diaryRef = patientRef.child(patientId).child("diary entry")
            val subscriberRef = patientRef.child(patientId).child("subscribers")

            // Add diary and subscriber to Firebase, Firebase auto-generated IDs
            val diaryId = patientRef.child(patientId).child("diary entry").push().key ?: ""
            diaryRef.child(diaryId).setValue(diaryEntry)

            val subscriberId = patientRef.child(patientId).child("subscribers").push().key ?: ""
            subscriberRef.child(subscriberId).setValue(subscriber)
                .addOnSuccessListener {
                    showToast("Patient added successfully")
                }
                .addOnFailureListener { exception ->
                    showToast("Failed to add patient: ${exception.localizedMessage}")
                }

        }

    }
    // ** if wanna skip login just comment onStart() code block
    // check if a user is logged in. If not, navigate to the com.example.project13application.LoginActivity
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}


