package com.example.nfc.patient.appointments

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R
import com.example.nfc.doctors.doctorlist
import com.example.nfc.doctors.doctorsadapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class Appointments : AppCompatActivity() {
    private lateinit var recylerarralist: ArrayList<doctorlist>
    private lateinit var myadapter:appointmentsadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val recyler=findViewById<RecyclerView>(R.id.appointmentsrecyler)

        recyler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyler.setHasFixedSize(true)
        recylerarralist = arrayListOf()

        myadapter= appointmentsadapter(recylerarralist)
        recyler.adapter=myadapter
        eventchanfelisterner()

    }

    private fun eventchanfelisterner() {
        var db = FirebaseFirestore.getInstance()
        db.collection("Doctors").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        recylerarralist.add(dc.document.toObject(doctorlist::class.java))
                    }
                }
                myadapter.notifyDataSetChanged()
            }

        })
    }
}