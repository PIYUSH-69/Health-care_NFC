package com.example.nfc.patient.sidenav

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.databinding.ActivityAabhaCardBinding
import com.example.nfc.databinding.ActivityPatientProfileBinding
import com.example.nfc.databinding.ActivityRecordsBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class AyuCard : AppCompatActivity() {

    private lateinit var binding: ActivityAabhaCardBinding
    private lateinit var patients: patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aabha_card)
        binding= ActivityAabhaCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid= Firebase.auth.currentUser?.uid.toString()

        runBlocking {
            patientcrud.getphotourl(uid){
                Glide.with(applicationContext)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(binding.abhaPhoto)
            }
        }

        patients = runBlocking { patientcrud.getpatient(uid) }!!
        binding.abhaDbName.setText(patients.FIRST_NAME+" "+patients.MIDDLE_NAME+" "+patients.LAST_NAME)
        binding.abhaDbNo.setText(patients.AYUSHMAN_ID)
        binding.abhaDbAddr.setText(patients.AYUSHMAN_ID+"@abdm")
        binding.abhaDbGender.setText(patients.GENDER)
        binding.abhaDbDob.setText(patients.DOB)
        binding.abhaDbMobile.setText(patients.PHONE_NUMBER)
    }
}