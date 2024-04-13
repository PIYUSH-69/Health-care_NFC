package com.example.nfc.patient.sidenav

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivityMedicalReportsBinding
import com.example.nfc.databinding.ActivityPatientProfileBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class MedicalReports : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalReportsBinding
    private lateinit var patients: patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_reports)
        binding = ActivityMedicalReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid= Firebase.auth.currentUser?.uid.toString()

//        patients= runBlocking { patientcrud.getpatient(uid) }!!
//        binding.editTextProfileFirstName.setText(patients.FIRST_NAME)
//        binding.editTextProfileMiddleName.setText(patients.MIDDLE_NAME)
//        binding.editTextProfileLastName.setText(patients.LAST_NAME)
//        binding.editTextStudentDob.setText(patients.DOB)
//        binding.editTextProfileGender.setText(patients.GENDER)
//        binding.editTextProfileEmail.setText(patients.EMAIL)
//        binding.editTextProfileMobile.setText(patients.PHONE_NUMBER)
//        binding.editTextProfileAadhar.setText(patients.ADHARCARD_NUMBER)
    }
}