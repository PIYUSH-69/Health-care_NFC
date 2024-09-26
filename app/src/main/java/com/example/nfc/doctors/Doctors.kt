package com.example.nfc.doctors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class Doctors : AppCompatActivity() {

    private lateinit var recylerarralist: ArrayList<doctorlist>
    private lateinit var myadapter: doctorsadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctors)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyler = findViewById<RecyclerView>(R.id.doctors)
        val add = findViewById<ImageView>(R.id.add_doctor)
        add.setOnClickListener {
            startActivity(Intent(this, newdoctor::class.java))
        }

        recyler.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyler.setHasFixedSize(true)
        recylerarralist = arrayListOf()

        myadapter = doctorsadapter(recylerarralist)
        recyler.adapter = myadapter
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