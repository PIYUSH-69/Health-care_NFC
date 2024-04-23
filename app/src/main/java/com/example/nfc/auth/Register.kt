package com.example.nfc.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nfc.R
import com.example.nfc.hospital.Hospital_main
import com.example.nfc.hospital.nfchospital.nfc_tag_hospital
import com.example.nfc.patient.Patient_main

class Register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val reg=findViewById<Button>(R.id.register)
        val sign=findViewById<Button>(R.id.signin)
        val signinhos=findViewById<Button>(R.id.signinhospital)

        reg.setOnClickListener {
            val intent= Intent(this, patient_personal::class.java)
            startActivity(intent)
        }

        sign.setOnClickListener {
            val intent= Intent(this, patient_signin::class.java)
            startActivity(intent)
        }

        signinhos.setOnClickListener {
            val intent= Intent(this, Hospital_main::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences= getSharedPreferences("counter", MODE_PRIVATE)
        val flag=sharedPreferences.getBoolean("flag",false)
        if (flag)
        {
            startActivity(Intent(this, Patient_main::class.java))
        }
    }
}