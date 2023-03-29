package com.example.project13application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DetailPatientActivity : AppCompatActivity() {
    private lateinit var note_recyclerView: RecyclerView
    private lateinit var sub_fam_recyclerView: RecyclerView
    private lateinit var sub_car_recyclerView: RecyclerView
    private lateinit var diarys: ArrayList<Diary>
    private lateinit var sub_mem:ArrayList<Subscriber>
    private lateinit var sub_car:ArrayList<Subscriber>
    private lateinit var database: DatabaseReference
    private lateinit var child_key: String
    private lateinit var user: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_patient)
        //get patient key
        child_key = intent.getStringExtra("key").toString()
        user = findViewById(R.id.p_username)
        //initial arraylist
        diarys = arrayListOf()
        sub_mem = arrayListOf()
        sub_car = arrayListOf()
        // //recyclerview initial
        //diary
        note_recyclerView = findViewById(R.id.p_notes)
        note_recyclerView.layoutManager = LinearLayoutManager(this)
        val note_adapter = DiaryAdapter(diarys)
        note_recyclerView.adapter = note_adapter

        //familymember
        sub_fam_recyclerView = findViewById(R.id.p_family_members)
        sub_fam_recyclerView.layoutManager = LinearLayoutManager(this)
        val fam_adapter = subAdapter(sub_mem)
        sub_fam_recyclerView.adapter = fam_adapter

        //caregivers
        sub_car_recyclerView = findViewById(R.id.p_caregivers)
        sub_car_recyclerView.layoutManager = LinearLayoutManager(this)
        val car_adapter = subAdapter(sub_car)
        sub_car_recyclerView.adapter = car_adapter

        //button
        val btn = findViewById<Button>(R.id.back_button)
        btn.setOnClickListener{
            val toDetails = Intent(this, PatientListActivity::class.java)
            startActivity(toDetails)
        }

        //inital db
        database = FirebaseDatabase.getInstance().getReference("patients").child(child_key)
        val fullname = database.child("firstName").toString() + " " + database.child("lastName").toString()
        user.setText(fullname).toString()

        //read all diarys
        val database_diary = database.child("diary entry")
        database_diary.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapShot in snapshot.children){
                        //read each patient with their key in database
                        val diary = dataSnapShot.getValue(Diary::class.java)
                        if(!diarys.contains(diary)){
                            diarys.add(diary!!)
                        }
                    }
                    //use the values to build the view
                    note_adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailPatientActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        //get all subs
        val database_sub = database.child("subscribers")
        database_sub.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapShot in snapshot.children){
                        //read each patient with their key in database
                        val subscriber = dataSnapShot.getValue(Subscriber::class.java)
                        if(subscriber!!.canEdit){
                            if(!sub_car.contains(subscriber)){
                                sub_car.add(subscriber!!)
                            }
                        }
                        else if(!sub_mem.contains(subscriber)){
                            sub_mem.add(subscriber!!)
                        }
                    }
                    //use the values to build the view
                    car_adapter.notifyDataSetChanged()
                    fam_adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailPatientActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}