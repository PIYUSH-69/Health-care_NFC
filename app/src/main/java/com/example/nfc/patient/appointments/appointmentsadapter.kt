package com.example.nfc.patient.appointments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R
import com.example.nfc.doctors.doctorlist

private lateinit var mContext: Context

class appointmentsadapter(private val doctorlist: ArrayList<doctorlist>) : RecyclerView.Adapter<appointmentsadapter.Myviewholder>() {
    class Myviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name=itemView.findViewById<TextView>(R.id.appdoctor)
        val domain=itemView.findViewById<TextView>(R.id.appdesignation)
        val specialization=itemView.findViewById<TextView>(R.id.appspec)
        val contact=itemView.findViewById<TextView>(R.id.appcontact)
        val button=itemView.findViewById<Button>(R.id.button10)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        mContext = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointmentcard,parent,false);
        return appointmentsadapter.Myviewholder(itemView)    }

    override fun getItemCount(): Int {
        return doctorlist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        val doctors: doctorlist =doctorlist[position]
        holder.name.text="Name: "+doctors.name
        holder.domain.text="Domain: "+doctors.domain
        holder.specialization.text="Specialization: "+doctors.specialization
        holder.contact.text="Contact: "+doctors.contact

        holder.button.setOnClickListener {
            val intent = Intent(holder.button.context, submitappointment::class.java)
            intent.putExtra("Doctorid", doctors.docuid)
            (mContext as Activity).startActivityForResult(intent,100)
        }

    }




}