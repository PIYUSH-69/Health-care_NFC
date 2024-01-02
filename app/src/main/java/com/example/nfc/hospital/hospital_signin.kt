package com.example.nfc.hospital

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nfc.R

class hospital_signin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_signin)

        val log=findViewById<Button>(R.id.log2)
        log.setOnClickListener {

            startActivity(Intent(this,HOSPITAL::class.java))
        }
    }
}