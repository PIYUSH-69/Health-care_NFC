package com.example.nfc.hospital.scantagband

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.databinding.ActivityPatientProfileBinding
import com.example.nfc.databinding.ActivityRecordPerosnalBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import kotlinx.coroutines.runBlocking

class record_perosnal : AppCompatActivity() {

    private lateinit var binding: ActivityRecordPerosnalBinding
    private lateinit var patients: patientwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record_perosnal)


        binding= ActivityRecordPerosnalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userid=intent.extras!!.getString("uid").toString()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        patients= runBlocking { patientcrud.getpatient(userid) }!!
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
            patientcrud.getphotourl(userid){
                Glide.with(applicationContext)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(binding.imageViewUserAvatar)
            }
        }






    }
}