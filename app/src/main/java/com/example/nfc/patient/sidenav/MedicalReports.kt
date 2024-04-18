package com.example.nfc.patient.sidenav

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.auth.Hashing
import com.example.nfc.databinding.ActivityMedicalReportsBinding
import com.example.nfc.databinding.ActivityPatientProfileBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class MedicalReports : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalReportsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_reports)

        binding=ActivityMedicalReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth=Firebase.auth.currentUser!!.uid
        binding.textView20.setText(auth)

        val token= runBlocking { Hashing.encode(auth)}
        binding.editTextText.setText(token)

        binding.button13.setOnClickListener {
            val a=binding.editTextText.text.toString()
            binding.textView21.setText(Hashing.deocode(a))

        }



    }




}