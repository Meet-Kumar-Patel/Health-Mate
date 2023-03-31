package com.example.project13application.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Database {
    companion object {
        fun getPatientDatabaseReference(): DatabaseReference {
            return FirebaseDatabase.getInstance().getReference("patients")
        }

        fun getSubscriberDatabaseReference(): DatabaseReference {
            return FirebaseDatabase.getInstance().getReference("subscribers")
        }
    }
}