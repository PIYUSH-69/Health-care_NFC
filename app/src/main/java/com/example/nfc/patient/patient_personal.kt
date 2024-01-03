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
import android.util.Patterns
import com.example.nfc.databinding.ActivityHospitalBinding
import com.example.nfc.databinding.ActivityPatientPersonalBinding


class patient_personal : AppCompatActivity() {

    private lateinit var binding: ActivityPatientPersonalBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityPatientPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)


   //     val reg = findViewById<Button>(R.id.button3)
//        val firstn = findViewById<TextInputEditText>(R.id.fname)
//        val midn = findViewById<TextInputEditText>(R.id.mname)
//        val lname = findViewById<TextInputEditText>(R.id.lname)
// val gender = findViewById<AutoCompleteTextView>(R.id.gender)
  //      var dob = findViewById<TextInputEditText>(R.id.dob)
//        val phonenum = findViewById<TextInputEditText>(R.id.pnum)
//        val adharnum = findViewById<TextInputEditText>(R.id.anum)
//        val email = findViewById<TextInputEditText>(R.id.email)
//        val pass = findViewById<TextInputEditText>(R.id.pass)
//        val cpass = findViewById<TextInputEditText>(R.id.cpass)

        val array = arrayListOf("Male", "Female", "Transgender")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        binding.gender.setAdapter(adapter)

        fname()
        mname()
        lname()
        gender()
        dob()
        pnum()
        email()
        anum()
        pass()
        cpass()



        binding.button3.setOnClickListener {
            startActivity(Intent(this, patient_medical::class.java))
        }

        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        val datePicker = datePickerBuilder.build()


        binding.dob.setOnClickListener {
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            // Format the selected date from the Unix timestamp
            val selectedDateInMillis = selection as Long
            val formattedDate = formatDate(selectedDateInMillis)
            binding.dob.setText(formattedDate)
        }
    }

    private fun cpass() {
        binding.cpass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.cpass.text.toString().isEmpty()) {
                    binding.cpasscon.error = "THIS FIELD IS REQUIRED"
                } else {
                    if (binding.cpass.text.toString()!=binding.pass.text.toString()){
                        binding.cpasscon.helperText = "Password dont match"
                    }
                    else {binding.cpasscon.helperText = null}
                }
            }
        }
    }


    private fun pass() {
        binding.pass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.pass.text.toString().isEmpty()) {
                    binding.passcon.helperText = "THIS FIELD IS REQUIRED"
                }
                else if (binding.pass.text.toString().length<8){
                        binding.passcon.helperText = "PASSWORD TOO SHORT"
                    }
                else if (!binding.pass.text.toString().matches(".*[A-Z].*".toRegex())){
                        binding.passcon.helperText = "MUST CONTAIN 1 UPPER-CASE CHARACTER"
                    }
                else if (!binding.pass.text.toString().matches(".*[a-z].*".toRegex())){
                    binding.passcon.helperText = "MUST CONTAIN 1 LOWER-CASE CHARACTER"
                }
                else if (!binding.pass.text.toString().matches(".*[@#\$%^&=+_].*".toRegex())){
                    binding.passcon.helperText = "MUST CONTAIN 1 SPECIAL CHARACTER (@#\$%^&=+_)"
                }
                else if (!binding.pass.text.toString().matches(".*[0-9].*".toRegex())){
                    binding.passcon.helperText = "MUST CONTAIN 1 NUMBER CHARACTER"
                }
                else   binding.passcon.helperText = null
            }
        }
    }

    private fun anum() {
        binding.anum.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.anum.text.toString().isEmpty()) {
                    binding.anumcon.helperText= "THIS FIELD IS REQUIRED"
                } else {
                    if (binding.anum.text.toString().length!=12){
                        binding.anumcon.helperText = "ADHAR NUMBER TOO SHORT"

                    }
                    else {binding.anumcon.helperText = null}
                }
            }
        }
    }

    private fun email() {
        binding.email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                var text=binding.email.text.toString()
                if (binding.email.text.toString().isEmpty()) {
                    binding.emailcon.helperText = "THIS FIELD IS REQUIRED"
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                        binding.emailcon.helperText = "INVALID EMAIL"
                    }
                    else {binding.emailcon.helperText = null}
                }
            }
        }

    }

    private fun pnum() {
        binding.pnum.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.pnum.text.toString().isEmpty()) {
                    binding.pnumcon.helperText = "THIS FIELD IS REQUIRED"
                } else {
                    if (binding.pnum.text.toString().length!=10){
                        binding.pnumcon.helperText = "PHONE NUMBER TOO SHORT"
                    }
                    else {binding.pnumcon.helperText = null}
                }
            }
        }
    }

    private fun dob() {
        binding.dob.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                if (binding.dob.text.toString().isEmpty()){
                    binding.dobcon.helperText="THIS FIELD IS REQUIRED"
                }
                else{
                    binding.dobcon.helperText=null
                }
            }
        }
    }

    private fun gender() {
        binding.gender.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                if (binding.gender.text.toString().isEmpty()){
                    binding.gendercon.helperText="THIS FIELD IS REQUIRED"
                }
                else{
                    binding.gendercon.helperText=null
                }
            }
        }
    }

    private fun lname() {
        binding.lname.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                if (binding.lname.text.toString().isEmpty()){
                    binding.lnamecon.helperText="THIS FIELD IS REQUIRED"
                }
                else{
                    binding.lnamecon.helperText=null
                }
            }
        }
    }

    private fun mname() {
        binding.mname.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                if (binding.mname.text.toString().isEmpty()){
                    binding.mnamecon.helperText="THIS FIELD IS REQUIRED"
                }
                else{
                    binding.mnamecon.helperText=null
                }
            }
        }
    }

    private fun fname() {
        binding.fname.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                if (binding.fname.text.toString().isEmpty()){
                    binding.fnamecon.helperText="THIS FIELD IS REQUIRED"
                }
                else{
                    binding.fnamecon.helperText=null
                }
            }
        }
    }


    private fun formatDate(timestampInMillis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return sdf.format(Date(timestampInMillis))
    }

    private fun submitform(){

        binding.button3.setOnClickListener {


        }
    }




}