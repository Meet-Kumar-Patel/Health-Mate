package com.example.project13application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PatientListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var patients: ArrayList<Patient>
    private lateinit var keys:ArrayList<String>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_list)
        //store data array
        patients = arrayListOf()
        keys = arrayListOf()

        //recyclerview initial
        recyclerView = findViewById(R.id.p_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PatientAdapter(patients,keys)
        recyclerView.adapter = adapter

        //firebase initial
        database = FirebaseDatabase.getInstance().getReference("patients")
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapShot in snapshot.children){
                        //read each patient with their key in database
                        val patient = dataSnapShot.getValue(Patient::class.java)
                        val key = dataSnapShot.key.toString()
                        if(!patients.contains(patient)){
                            patients.add(patient!!)
                            keys.add(key!!)
                        }
                    }
                    //use the values to build the view
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PatientListActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}