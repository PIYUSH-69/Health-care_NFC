package com.example.nfc.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.example.nfc.R
import com.example.nfc.databinding.ActivityPatientMedicalBinding

class patient_medical : AppCompatActivity() {

    private lateinit var binding: ActivityPatientMedicalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityPatientMedicalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val array = arrayListOf("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        binding.bldgrp.setAdapter(adapter)

        height()
        weight()
        bldgrp()
        ayush()

        val reg=findViewById<Button>(R.id.button)
        reg.setOnClickListener {

            if (submitform()){

                startActivity(Intent(this,MainActivity::class.java))

            }
            else{

                Toast.makeText(this, "gsdgdsgds", Toast.LENGTH_SHORT).show()
            }





        }

    }

    private fun submitform(): Boolean {

        binding.ayushcon.helperText=validateayush()
        binding.bldgrpcon.helperText=validatebldgrp()
        binding.weightcon.helperText= validateweight()
        binding.heightcon.helperText= validateheight()

       val a= binding.ayushcon.helperText==null
       val b= binding.bldgrpcon.helperText==null
       val c= binding.weightcon.helperText==null
       val d= binding.heightcon.helperText==null


        return a && b && c && d

    }


    private fun height() {
        binding.height.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.heightcon.helperText = validateheight()
            }
        }
    }

    private fun validateheight(): String? {
        val text = binding.height.text.toString()
        if (binding.height.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }
    }

    private fun weight() {
        binding.weight.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.weightcon.helperText = validateweight()
            }
        }
    }

    private fun validateweight(): String? {
        if (binding.weight.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }
    }

    private fun bldgrp() {
        binding.bldgrp.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.bldgrpcon.helperText = validatebldgrp()
            }
        }
    }

    private fun validatebldgrp(): String? {
        if (binding.bldgrp.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }
    }

    private fun ayush() {
        binding.ayush.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.ayushcon.helperText = validateayush()
            }
        }
    }

    private fun validateayush(): String? {
        if (binding.ayush.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
            }
        }



}
