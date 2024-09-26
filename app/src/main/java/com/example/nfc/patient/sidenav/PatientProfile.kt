package com.example.nfc.patient.sidenav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.databinding.ActivityPatientProfileBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class PatientProfile : AppCompatActivity() {

    private lateinit var binding: ActivityPatientProfileBinding
    private lateinit var patients: patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_profile)
        binding = ActivityPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = Firebase.auth.currentUser?.uid.toString()

        patients = runBlocking { patientcrud.getpatient(uid) }!!
        binding.textViewProfileName.text =
            patients.FIRST_NAME + " " + patients.MIDDLE_NAME + " " + patients.LAST_NAME
        binding.editTextProfileFirstName.setText(patients.FIRST_NAME)
        binding.editTextProfileMiddleName.setText(patients.MIDDLE_NAME)
        binding.editTextProfileLastName.setText(patients.LAST_NAME)
        binding.editTextStudentDob.setText(patients.DOB)
        binding.editTextProfileGender.setText(patients.GENDER)
        binding.editTextProfileEmail.setText(patients.EMAIL)
        binding.editTextProfileMobile.setText(patients.PHONE_NUMBER)
        binding.editTextProfileAadhar.setText(patients.ADHARCARD_NUMBER)

        runBlocking {
            patientcrud.getphotourl(uid) {
                Glide.with(applicationContext)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(binding.imageViewUserAvatar)
            }
        }


    }
}