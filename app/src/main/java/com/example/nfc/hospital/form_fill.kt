package com.example.nfc.hospital

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivityFormFillBinding
import com.example.nfc.databinding.ActivityPatientSigninBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class form_fill : AppCompatActivity() {

    private lateinit var binding: ActivityFormFillBinding
    private lateinit var patients: patientwrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_fill)
        binding= ActivityFormFillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uid=intent.extras!!.getString("uid").toString()
        Firebase.firestore.collection("Patient").document(uid).update("qr","true")

        patients= runBlocking { patientcrud.getpatient(uid) }!!
        binding.patient.setText(patients.FIRST_NAME+" "+patients.MIDDLE_NAME+" "+patients.LAST_NAME)
        binding.dob.setText(patients.DOB)
        binding.gender.setText(patients.GENDER)
        binding.adhar.setText(patients.ADHARCARD_NUMBER)
        binding.abha.setText(patients.AYUSHMAN_ID)
        binding.phoneno.setText(patients.PHONE_NUMBER)



    }

}