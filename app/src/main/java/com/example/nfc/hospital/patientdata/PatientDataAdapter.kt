package com.example.nfc.hospital.patientdata

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import kotlinx.coroutines.runBlocking
import android.view.View
import android.widget.Button
import com.example.nfc.hospital.patientdata.medicalReport.MedicalReport
import com.example.nfc.hospital.scantagband.record_medical
import com.example.nfc.hospital.scantagband.record_perosnal

class PatientDataAdapter(private val registeredUsersList : ArrayList<patientwrapper>, private val context: Context): RecyclerView.Adapter<PatientDataAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val patient_name : TextView = itemView.findViewById(R.id.patient_name)
        val patient_gender : TextView = itemView.findViewById(R.id.patient_gender)
        val patient_phone_no : TextView = itemView.findViewById(R.id.patient_phone_no)
        val patient_dp : ImageView = itemView.findViewById(R.id.patient_dp)
        val profileDetailsButton: Button = itemView.findViewById(R.id.profileButton)
        val healthRecordButton: Button = itemView.findViewById(R.id.healthButton)
        val medicalReportButton: Button = itemView.findViewById(R.id.medicalButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.patient_card,parent,false);
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user : patientwrapper = registeredUsersList[position]
        holder.patient_name.text=user.FIRST_NAME+" "+user.MIDDLE_NAME+" "+user.LAST_NAME
        holder.patient_gender.text = user.GENDER
        holder.patient_phone_no.text = user.PHONE_NUMBER

        runBlocking {
            val uid= user.USER_ID.toString()
            patientcrud.getphotourl(uid){
                Glide.with(holder.patient_dp.context)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(holder.patient_dp)
            }
        }

        holder.profileDetailsButton.setOnClickListener {
            val intent = Intent(context, record_perosnal::class.java).putExtra("uid",user.USER_ID.toString())
            context.startActivity(intent)
        }

        holder.healthRecordButton.setOnClickListener {
            val intent = Intent(context, record_medical::class.java).putExtra("uid",user.USER_ID.toString())
            context.startActivity(intent)
        }

        holder.medicalReportButton.setOnClickListener {
            val intent = Intent(context, MedicalReport::class.java).putExtra("uid",user.USER_ID.toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return registeredUsersList.size
    }
}