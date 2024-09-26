package com.example.nfc.hospital.hospitalsidenav

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R
import com.example.nfc.databinding.ActivityHospitalAppointmentsBinding
import com.example.nfc.patient.appointments.appointmentwrapper
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class HospitalAppointments : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalAppointmentsBinding
    private lateinit var recylerarralist: ArrayList<appointmentwrapper>
    private lateinit var myadapter: hospitalAppointmentsadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHospitalAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_hospital_appointments)

        val recylerview = findViewById<RecyclerView>(R.id.hosrecyler)

        recylerview.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recylerview.setHasFixedSize(true)
        recylerarralist = arrayListOf()

        myadapter = hospitalAppointmentsadapter(recylerarralist)
        recylerview.adapter = myadapter
        eventchangelisterner()
    }

    private fun eventchangelisterner() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Appointments").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        recylerarralist.add(dc.document.toObject(appointmentwrapper::class.java))
                        Log.d(TAG, "FIRE:: " + recylerarralist.toString())
                    }
                }
                myadapter.notifyDataSetChanged()
            }
        })
    }
}