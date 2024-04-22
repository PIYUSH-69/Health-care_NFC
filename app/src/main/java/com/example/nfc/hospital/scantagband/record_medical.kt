package com.example.nfc.hospital.scantagband

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.databinding.ActivityRecordMedicalBinding
import com.example.nfc.databinding.ActivityRecordsBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import kotlinx.coroutines.runBlocking

class record_medical : AppCompatActivity() {

    private lateinit var binding: ActivityRecordMedicalBinding
    private lateinit var patients: patientwrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record_medical)


        val uid=intent.extras!!.getString("uid").toString()


        binding = ActivityRecordMedicalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        patients= runBlocking { patientcrud.getpatient(uid) }!!
        binding.textViewProfileName.text = patients.FIRST_NAME+" "+patients.MIDDLE_NAME+" "+patients.LAST_NAME
        binding.editTextHeight.setText(patients.HEIGHT+" m")
        binding.editTextWeight.setText(patients.WEIGHT+" kg")
        binding.editTextBloodGroup.setText(patients.BLOOD_GROUP)
        binding.editTextBloodPressure.setText(patients.BLOOD_PRESSURE)
        binding.editTextDiabetes.setText(patients.DIABETES)
        binding.editTextAsthama.setText(patients.ASTHAMA)
        binding.editTextAllergies.setText(patients.Allergies)
        binding.editTextSurgeries.setText(patients.SURGERIES)

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