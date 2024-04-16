package com.example.nfc.hospital.hospitalsidenav

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.doctors.doctorwrapper
import com.example.nfc.patient.appointments.appointmentwrapper
import com.example.nfc.patient.patientcrud
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.runBlocking

private lateinit var mContext: Context

class hospitalAppointmentsadapter(private val appointmentlist: ArrayList<appointmentwrapper>) :
    RecyclerView.Adapter<hospitalAppointmentsadapter.Myviewholder>() {
    class Myviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var patientname=itemView.findViewById<TextView>(R.id.patientname)
        var doctorname=itemView.findViewById<TextView>(R.id.textView13)
        var date=itemView.findViewById<TextView>(R.id.textView15)
        var slot=itemView.findViewById<TextView>(R.id.textView16)
        val image=itemView.findViewById<ImageView>(R.id.imageView3)
        val button=itemView.findViewById<Button>(R.id.button10)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        mContext = parent.context

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointmentcardhospital,parent,false);
        return Myviewholder(itemView)    }

    override fun getItemCount(): Int {
        return appointmentlist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        val appointment:appointmentwrapper=appointmentlist[position]

        val docuid=appointment.doctorid
        val uid=appointment.uid

        runBlocking {
            patientcrud.getphotourl(uid!!){
                Glide.with(mContext)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(holder.image)
            }
        }




        val patients=runBlocking{ patientcrud.getpatient(uid!!) }!!
        val name=patients.FIRST_NAME+" "+patients.LAST_NAME

        val doctorlist= runBlocking { doctorwrapper.getdocotr(docuid!!) }
        val docname=doctorlist.name

        holder.button.setOnClickListener {

            if (ContextCompat.checkSelfPermission(mContext,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    mContext as Activity, arrayOf(android.Manifest.permission.CALL_PHONE),
                    IntentIntegrator.REQUEST_CODE
                )
            } else {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + patients.PHONE_NUMBER))
                startActivity(mContext,intent,null)
            }
        }


        holder.date.text=appointment.date
        holder.slot.text=appointment.timeslot
        holder.patientname.text=name
        holder.doctorname.text="Doctor: "+docname


        Log.d(TAG, "onBindViewHolder: "+appointment.doctorid.toString())


    }




    }


