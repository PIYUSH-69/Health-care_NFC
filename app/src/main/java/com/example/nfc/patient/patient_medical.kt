package com.example.nfc.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nfc.R

class patient_medical : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_medical)

        val reg=findViewById<Button>(R.id.button)
        reg.setOnClickListener {

            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}