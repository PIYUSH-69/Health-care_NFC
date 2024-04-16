package com.example.nfc.patient.sidenav

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nfc.R
import kotlinx.coroutines.runBlocking
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.databinding.ActivityFormFillBinding
import com.example.nfc.databinding.ActivityPatientProfileBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PatientProfile : AppCompatActivity() {

    private lateinit var binding: ActivityPatientProfileBinding
    private lateinit var patients: patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_profile)
        binding= ActivityPatientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid= Firebase.auth.currentUser?.uid.toString()

        patients= runBlocking { patientcrud.getpatient(uid) }!!
        binding.textViewProfileName.text = patients.FIRST_NAME+" "+patients.MIDDLE_NAME+" "+patients.LAST_NAME
        binding.editTextProfileFirstName.setText(patients.FIRST_NAME)
        binding.editTextProfileMiddleName.setText(patients.MIDDLE_NAME)
        binding.editTextProfileLastName.setText(patients.LAST_NAME)
        binding.editTextStudentDob.setText(patients.DOB)
        binding.editTextProfileGender.setText(patients.GENDER)
        binding.editTextProfileEmail.setText(patients.EMAIL)
        binding.editTextProfileMobile.setText(patients.PHONE_NUMBER)
        binding.editTextProfileAadhar.setText(patients.ADHARCARD_NUMBER)

        runBlocking {
                patientcrud.getphotourl(uid){
                    Glide.with(applicationContext)
                        .setDefaultRequestOptions(RequestOptions())
                        .load(it)
                        .into(binding.imageViewUserAvatar)
                }
        }


    }
}