package com.example.project13application

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//data adapter for patients
class PatientAdapter(val patients:ArrayList<Patient>, val keys:ArrayList<String>):RecyclerView.Adapter<PatientAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val patientFN:TextView = itemView.findViewById(R.id.fn_patient)
        val key:TextView = itemView.findViewById(R.id.key_patient)
        val btn: Button = itemView.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val patientView = LayoutInflater.from(parent.context).inflate(R.layout.patient_list,parent,false)
        return MyViewHolder(patientView)
    }

    override fun getItemCount(): Int {
        return patients.size
    }
    //link to the patient_list.xml as elements
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.patientFN.text = patients[position].firstName
        holder.key.text = keys[position]
        val context = holder.btn.context
        holder.btn.setOnClickListener {
            val toDetails = Intent(context, DetailPatientActivity::class.java)
            toDetails.putExtra("key",keys[position])
            context.startActivity(toDetails)

        }
    }
}