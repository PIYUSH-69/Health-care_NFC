package com.example.nfc.patient.sidenav

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nfc.R
import kotlinx.coroutines.runBlocking
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientProfile : AppCompatActivity() {

    private val REQUEST_CODE_IMAGE_UPSERT = 103
    private lateinit var ivStudentAvatar: ImageView
    private lateinit var db: FirebaseFirestore
    private lateinit var edit_text_profile_first_name: TextInputEditText
    private lateinit var edit_text_profile_middle_name: TextInputEditText
    private lateinit var edit_text_profile_last_name: TextInputEditText
    private lateinit var edit_text_student_dob: TextInputEditText
    private lateinit var edit_text_profile_gender: TextInputEditText
    private lateinit var edit_text_profile_email: TextInputEditText
    private lateinit var edit_text_profile_mobile: TextInputEditText
    private lateinit var edit_text_profile_aadhar: TextInputEditText
    private lateinit var headerName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_profile)

        db = FirebaseFirestore.getInstance()
        edit_text_profile_first_name = findViewById(R.id.edit_text_profile_first_name)
        edit_text_profile_middle_name = findViewById(R.id.edit_text_profile_middle_name)
        edit_text_profile_last_name = findViewById(R.id.edit_text_profile_last_name)
        edit_text_student_dob = findViewById(R.id.edit_text_student_dob)
        edit_text_profile_gender = findViewById(R.id.edit_text_profile_gender)
        edit_text_profile_email = findViewById(R.id.edit_text_profile_email)
        edit_text_profile_mobile = findViewById(R.id.edit_text_profile_mobile)
        edit_text_profile_aadhar = findViewById(R.id.edit_text_profile_aadhar)
        headerName = findViewById(R.id.text_view_profile_name)

        fetchPatientData()

    }

    private fun fetchPatientData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {user ->
            db.collection("Patient")
                .document(user.uid)
                .get()
                .addOnSuccessListener {document ->
                    if (document != null && document.exists()) {
                        val firstName = document.getString("FIRST NAME")
                        val middleName = document.getString("MIDDLE NAME")
                        val lastName = document.getString("LAST NAME")
                        val dob = document.getString("DOB")
                        val gender = document.getString("GENDER")
                        val email = document.getString("EMAIL")
                        val contact = document.getString("PHONE NUMBER")
                        val aadhar = document.getString("ADHARCARD NUMBER")
                        headerName.text = firstName+" "+lastName

                        edit_text_profile_first_name.setText(firstName)
                        edit_text_profile_middle_name.setText(middleName)
                        edit_text_profile_last_name.setText(lastName)
                        edit_text_student_dob.setText(dob)
                        edit_text_profile_gender.setText(gender)
                        edit_text_profile_email.setText(email)
                        edit_text_profile_mobile.setText(contact)
                        edit_text_profile_aadhar.setText(aadhar)

                    }
                }
        }
    }
}