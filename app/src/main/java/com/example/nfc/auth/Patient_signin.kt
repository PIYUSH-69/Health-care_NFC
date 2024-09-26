package com.example.nfc.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivityPatientSigninBinding
import com.example.nfc.patient.Patient_main
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class patient_signin : AppCompatActivity() {

    private lateinit var binding: ActivityPatientSigninBinding

    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var check = findViewById<CheckBox>(R.id.checkBox)
        val auth = Firebase.auth

        valname()
        valpass()

        val sharedPreferences = getSharedPreferences("counter", MODE_PRIVATE)

        val log = findViewById<Button>(R.id.log)
        log.setOnClickListener {

            var check1 = check.isChecked

            validatename()
            validatepass()
            val name = binding.name.text.toString()
            val pass = binding.pass.text.toString()

            if (submit()) {

                auth.signInWithEmailAndPassword(name, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userid = auth.uid
                        sharedPreferences.edit().apply {
                            putBoolean("flag", check1)
                            putString("uid", userid)
                        }.apply()
                        MotionToast.darkColorToast(
                            this, "WELCOME",
                            "Authentication Successfull",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(
                                this,
                                www.sanju.motiontoast.R.font.helvetica_regular
                            )
                        )


                        startActivity(Intent(this, Patient_main::class.java))
                    } else {
                        MotionToast.darkColorToast(
                            this, "",
                            "Wrong Credentials!!",
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(
                                this,
                                www.sanju.motiontoast.R.font.helvetica_regular
                            )
                        )
                    }
                }
            } else {
                MotionToast.darkColorToast(
                    this, "Enter All Details!",
                    "Enter EmailID and Password",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
                )
            }
        }
    }

    private fun submit(): Boolean {

        binding.namecon.helperText = validatename()
        binding.passcon.helperText = validatepass()

        val a = binding.namecon.helperText == null
        val b = binding.passcon.helperText == null

        return a && b

    }

    private fun valpass() {
        binding.pass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.passcon.helperText = validatepass()
            }
        }
    }

    private fun validatepass(): String? {
        if (binding.pass.text.toString().isEmpty()) {
            return "ENTER PASSWORD"
        } else return null
    }

    private fun valname() {
        binding.name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.namecon.helperText = validatename()
            }
        }
    }

    private fun validatename(): String? {
        if (binding.name.text.toString().isEmpty()) {
            return "ENTER EMAIL"
        } else return null
    }
}