package com.example.nfc.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nfc.R

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

            val intent= Intent(this, hospital_signin::class.java)
            startActivity(intent)


        }



    }
}