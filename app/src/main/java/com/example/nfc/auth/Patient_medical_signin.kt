package com.example.nfc.auth


import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivityPatientMedicalBinding
import com.example.nfc.patient.Patient_main
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class patient_medical_signin : AppCompatActivity() {

    private lateinit var binding: ActivityPatientMedicalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientMedicalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val array = arrayListOf("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        binding.bldgrp.setAdapter(adapter)

        height()
        weight()
        ayush()
        bldgrp()

        val Auth = Firebase.auth

        val reg = findViewById<Button>(R.id.button)
        val user = Auth.uid.toString()

        binding.butto.setOnClickListener {

            val url = "https://abdm.gov.in/"
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            startActivity(i)

        }




        reg.setOnClickListener {


            if (submitform()) {

                val height = binding.height.text.toString()
                val wieght = binding.weight.text.toString()
                val bldgrp = binding.bldgrp.text.toString()
                val bloodpressure = binding.checkBox3.isChecked.toString()
                val diabetes = binding.checkBox4.isChecked.toString()
                val asthama = binding.checkBox5.isChecked.toString()
                val illness = binding.allergies.text.toString()
                val Allergies = binding.allergies.text.toString()
                val ayush = binding.ayush.text.toString()

                val db = Firebase.firestore
                var mdetails = HashMap<String, String>()
                mdetails.put("HEIGHT", height)
                mdetails.put("WEIGHT", wieght)
                mdetails.put("BLOOD_GROUP", bldgrp)
                mdetails.put("BLOOD_PRESSURE", bloodpressure)
                mdetails.put("DIABETES", diabetes)
                mdetails.put("ASTHAMA", asthama)
                mdetails.put("SURGERIES", illness)
                mdetails.put("Allergies", Allergies)
                mdetails.put("AYUSHMAN_ID", ayush)

                db.collection("Patient")
                    .document(user)
                    .set(mdetails, SetOptions.merge())
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added ")
                        MotionToast.darkColorToast(
                            this, "Form submitted!",
                            "Registeration successful",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.SHORT_DURATION,
                            ResourcesCompat.getFont(
                                this,
                                www.sanju.motiontoast.R.font.helvetica_regular
                            )
                        )
                        startActivity(Intent(this, Patient_main::class.java))
                        Toast.makeText(this, "ADDED VALUEs", Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }

            } else {
                MotionToast.darkColorToast(
                    this, "Validation Failed!",
                    "Enter All Details!l",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
                )
            }
        }
    }

    private fun submitform(): Boolean {

        binding.ayushcon.helperText = validateayush()
        binding.bldgrpcon.helperText = validatebldgrp()
        binding.weightcon.helperText = validateweight()
        binding.heightcon.helperText = validateheight()

        val a = binding.ayushcon.helperText == null
        val b = binding.bldgrpcon.helperText == null
        val c = binding.weightcon.helperText == null
        val d = binding.heightcon.helperText == null

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
        if (binding.height.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else if (binding.height.text.toString().toInt() > 272) {
            return "INVALID HEIGHT"
        } else return null
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
        } else if (binding.weight.text.toString().toInt() > 200) {
            return "INVALID WEIGHT"
        } else return null
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
        } else if (binding.ayush.text.toString().length < 14) {
            return "TOO SHORT"
        } else return null
    }
}
