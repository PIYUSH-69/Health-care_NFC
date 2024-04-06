package com.example.nfc.doctors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R

class doctorsadapter(private val doctorlist: ArrayList<doctorlist>) :RecyclerView.Adapter<doctorsadapter.Myviewholder>() {
    class Myviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name=itemView.findViewById<TextView>(R.id.textView10)
        val domain=itemView.findViewById<TextView>(R.id.textView11)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.doctorcard,parent,false);
        return doctorsadapter.Myviewholder(itemView)    }

    override fun getItemCount(): Int {
        return doctorlist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        val doctors: doctorlist=doctorlist[position]
        holder.name.text=doctors.name
        holder.domain.text=doctors.domain

    }




}