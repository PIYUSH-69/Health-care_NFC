package com.example.nfc.patient.sidenav

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivityRecordsBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class Records : AppCompatActivity() {

    private lateinit var binding: ActivityRecordsBinding
    private lateinit var patients: patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        binding = ActivityRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid= Firebase.auth.currentUser?.uid.toString()

        patients= runBlocking { patientcrud.getpatient(uid) }!!
        binding.editTextHeight.setText(patients.HEIGHT+" m")
        binding.editTextWeight.setText(patients.WEIGHT+" kg")
        binding.editTextBloodGroup.setText(patients.BLOOD_GROUP)
        binding.editTextBloodPressure.setText(patients.BLOOD_PRESSURE)
        binding.editTextDiabetes.setText(patients.DIABETES)
        binding.editTextAsthama.setText(patients.ASTHAMA)
        binding.editTextAllergies.setText(patients.Allergies)
        binding.editTextSurgeries.setText(patients.SURGERIES)
    }
}