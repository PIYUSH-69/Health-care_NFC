package com.example.nfc.hospital.hospitalsidenav

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.databinding.ActivityProfileDetailsBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class ProfileDetails : AppCompatActivity() {
    private lateinit var binding : ActivityProfileDetailsBinding
    private lateinit var patients : patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid= Firebase.auth.currentUser?.uid.toString()

        patients= runBlocking { patientcrud.getpatient(uid) }!!
        binding.textViewProfileName.text = patients.FIRST_NAME+" "+patients.MIDDLE_NAME+" "+patients.LAST_NAME
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