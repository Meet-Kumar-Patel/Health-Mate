package com.example.project13application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class subAdapter(val subs:ArrayList<Subscriber>): RecyclerView.Adapter<subAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val subname: TextView = itemView.findViewById(R.id.sub_name)
        val subtype: TextView = itemView.findViewById(R.id.sub_type)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): subAdapter.MyViewHolder {
        val subView = LayoutInflater.from(parent.context).inflate(R.layout.subscriber,parent,false)
        return subAdapter.MyViewHolder(subView)
    }

    override fun getItemCount(): Int {
        return subs.size
    }
    //link to the patient_note.xml as elements
    override fun onBindViewHolder(holder: subAdapter.MyViewHolder, position: Int) {
        holder.subname.text = subs[position].username
        holder.subtype.text = subs[position].type.toString()
    }
}