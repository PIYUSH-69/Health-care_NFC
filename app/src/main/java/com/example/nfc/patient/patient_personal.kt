package com.example.nfc.patient

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.nfc.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.util.Date
import java.util.Locale
import android.icu.text.SimpleDateFormat


class patient_personal : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_personal)

        val reg = findViewById<Button>(R.id.button3)
        val firstn = findViewById<TextInputEditText>(R.id.fname)
        val midn = findViewById<TextInputEditText>(R.id.mname)
        val lname = findViewById<TextInputEditText>(R.id.lname)
        val gender = findViewById<AutoCompleteTextView>(R.id.gender)
        var dob = findViewById<TextInputEditText>(R.id.dob)
        val phonenum = findViewById<TextInputEditText>(R.id.pnum)
        val adharnum = findViewById<TextInputEditText>(R.id.anum)
        val email = findViewById<TextInputEditText>(R.id.email)
        val pass = findViewById<TextInputEditText>(R.id.pass)
        val cpass = findViewById<TextInputEditText>(R.id.cpass)

        val array = arrayListOf("Male", "Female", "Transgender")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        gender.setAdapter(adapter)



        reg.setOnClickListener {


            startActivity(Intent(this, patient_medical::class.java))

        }

        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        val datePicker = datePickerBuilder.build()


        dob.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            // Format the selected date from the Unix timestamp
            val selectedDateInMillis = selection as Long
            val formattedDate = formatDate(selectedDateInMillis)
            dob.setText(formattedDate)
        }

    }

    private fun formatDate(timestampInMillis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return sdf.format(Date(timestampInMillis))
    }
}