package com.example.nfc.hospital

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nfc.R
import com.example.nfc.databinding.ActivityHospitalSigninBinding
import com.example.nfc.patient.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class hospital_signin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivityHospitalSigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_signin)

        binding=ActivityHospitalSigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth= Firebase.auth

        valname()
        valpass()


        binding.log2.setOnClickListener {


            validatename()
            validatepass()

            val name=binding.name.text.toString()
            val pass=binding.pass.text.toString()

            if (submit()){

                auth.signInWithEmailAndPassword(name,pass).addOnSuccessListener {

                    startActivity(Intent(this,HOSPITAL::class.java))

                }.addOnFailureListener {

                    binding.textview3.text="INVALID EMAIL OR PASSWORD"
                    binding.textview3.setTextColor(resources.getColor(R.color.red))

                }
            }
        }
    }

    private fun submit(): Boolean {

        binding.namecon.helperText=validatename()
        binding.passcon.helperText=validatepass()

        val a=binding.namecon.helperText==null
        val b=binding.passcon.helperText==null

        return a && b

    }

    private fun valpass() {
        binding.pass.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                binding.passcon.helperText=validatepass()
            }
        }
    }
    private fun validatepass(): String? {
        if (binding.pass.text.toString().isEmpty()){
            return "ENTER PASSWORD"
        }
        else return null
    }

    private fun valname() {
        binding.name.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                binding.namecon.helperText=validatename()
            }
        }
    }

    private fun validatename(): String? {
        if (binding.name.text.toString().isEmpty()){
            return "ENTER EMAIL"
        }
        else return null
    }


}