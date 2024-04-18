package com.example.nfc.hospital.nfchospital

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.databinding.ActivityFormFillBinding
import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class form_fill : AppCompatActivity() {

    private lateinit var binding: ActivityFormFillBinding
    private lateinit var patients: patientwrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_form_fill)
        binding= ActivityFormFillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid=intent.extras!!.getString("uid").toString()
        Firebase.firestore.collection("Patient").document(uid).update("qr","true")

        try {
            patients= runBlocking { patientcrud.getpatient(uid) }!!
        }catch (e :Exception){
            Toast.makeText(this, "wrong user id", Toast.LENGTH_SHORT).show()
        }
        binding.patient.setText(patients.FIRST_NAME+" "+patients.MIDDLE_NAME+" "+patients.LAST_NAME)
        binding.dob.setText(patients.DOB)
        binding.gender.setText(patients.GENDER)
        binding.adhar.setText(patients.ADHARCARD_NUMBER)
        binding.abha.setText(patients.AYUSHMAN_ID)
        binding.phoneno.setText(patients.PHONE_NUMBER)
        binding.blood.setText(patients.BLOOD_GROUP)

        runBlocking {
            patientcrud.getphotourl(uid){
                Glide.with(applicationContext)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(binding.imageView2)
            }
        }



        binding.button9.setOnClickListener {
            val a=binding.cause.text.toString()
            Log.d(ContentValues.TAG, "onCreate: "+a)
            val b=binding.guardian.text.toString()
            val db=Firebase.firestore

            var details= HashMap<String,String>()
            details.put("entryyytime", System.currentTimeMillis().toString())
            details.put("uid",uid)
            details.put("cause",a)
            details.put("guardian",b)

            db.collection("Hospital")
                .document(uid)
                .set(details)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added ")
                    MotionToast.darkColorToast(this,"Form submitted!",
                        "Form Filled Successfully",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                        startActivity(Intent(this, nfc_tag_hospital::class.java))

                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }

        }

    }
}