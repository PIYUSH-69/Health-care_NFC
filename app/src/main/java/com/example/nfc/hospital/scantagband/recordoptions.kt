package com.example.nfc.hospital.scantagband

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.hospital.patientdata.medicalReport.MedicalReport

class recordoptions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recordoptions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userid = intent.extras!!.getString("uid").toString()
        val personal = findViewById<Button>(R.id.button15)
        val medical = findViewById<Button>(R.id.button16)
        val report = findViewById<Button>(R.id.button17)

        personal.setOnClickListener {
            startActivity(Intent(this, record_perosnal::class.java).putExtra("uid", userid))
        }
        medical.setOnClickListener {
            startActivity(Intent(this, record_medical::class.java).putExtra("uid", userid))
        }
        report.setOnClickListener {
            startActivity(Intent(this, MedicalReport::class.java).putExtra("uid", userid))
        }

    }
}