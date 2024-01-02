package com.example.nfc.patient

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nfc.R

class patient_signin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_signin)

        val log=findViewById<Button>(R.id.log)
        log.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}