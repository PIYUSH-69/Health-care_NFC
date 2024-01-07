package com.example.nfc.patient

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.nfc.databinding.ActivityPatientPersonalBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.Date
import java.util.Locale
import java.util.Objects


class patient_personal : AppCompatActivity() {

    private lateinit var binding: ActivityPatientPersonalBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //spinner
        val array = arrayListOf("Male", "Female", "Transgender")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        binding.gender.setAdapter(adapter)

        //datepicker
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        val datePicker = datePickerBuilder.build()

        binding.dob.setOnClickListener {
            if (!datePicker.isAdded)
            {
                datePicker.show(supportFragmentManager, "DatePicker")
            }
        }
        datePicker.addOnPositiveButtonClickListener { selection ->
            // Format the selected date from the Unix timestamp
            val selectedDateInMillis = selection as Long
            val formattedDate = formatDate(selectedDateInMillis)
            binding.dob.setText(formattedDate)
        }

        //validation
        fname()
        mname()
        lname()
        gender()
        pnum()
        email()
        anum()
        pass()
        cpass()

        //submitform
        auth=Firebase.auth

        binding.button3.setOnClickListener {
            if(submitform())
            {
                val fname=binding.fname.text.toString()
                val mname=binding.mname.text.toString()
                val lname=binding.lname.text.toString()
                val anum=binding.anum.text.toString()
                val pnum=binding.pnum.text.toString()
                val dob=binding.dob.text.toString()
                val gender=binding.gender.text.toString()
                val emailid=binding.email.text.toString()
                val pass=binding.pass.text.toString()

                auth.createUserWithEmailAndPassword(emailid, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val user = auth.uid.toString()
                            val db=Firebase.firestore
                            var details= HashMap<String,String>()
                            details.put("FIRST NAME",fname)
                            details.put("MIDDLE NAME",mname)
                            details.put("LAST NAME",lname)
                            details.put("GENDER",gender)
                            details.put("DOB", dob)
                            details.put("PHONE NUMBER",pnum)
                            details.put("ADHARCARD NUMBER",anum)
                            details.put("EMAIL",emailid)
                            details.put("PASSWORD",pass)

                            db.collection("Patient")
                                .document(user)
                                .set(details)
                                .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "DocumentSnapshot added ")
                                    MotionToast.darkColorToast(this,"Form submitted!",
                                        "JINKLAS BHAVA",
                                        MotionToastStyle.SUCCESS,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                                    startActivity(Intent(this,patient_medical::class.java))

                                Toast.makeText(this, "ADDED DETAILS", Toast.LENGTH_SHORT).show()

                                }.addOnFailureListener { e ->
                                    Log.w(ContentValues.TAG, "Error adding document", e)
                                }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            MotionToast.darkColorToast(this,"Authentication Failed!",
                                "JINKLAS BHAVA",
                                MotionToastStyle.SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
                        }
                    }
            }

            else{
                MotionToast.darkColorToast(this,"Enter All Details!",
                    "HAGLAS BHAVA",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
            }
        }
    }

    //functions----->
    private fun cpass() {
        binding.cpass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.cpasscon.helperText = validatecpass()
            }
        }
    }


    private fun validatecpass(): String? {
        if (binding.cpass.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            if (binding.cpass.text.toString() != binding.pass.text.toString()) {
                return "Password dont match"
            } else {
                return null
            }
        }
    }


    private fun pass() {
        binding.pass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.passcon.helperText = validatepass()
            }
        }
    }

    private fun validatepass(): String? {
        if (binding.pass.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else if (binding.pass.text.toString().length < 8) {
            return "PASSWORD TOO SHORT"
        } else if (!binding.pass.text.toString().matches(".*[A-Z].*".toRegex())) {
            return "MUST CONTAIN 1 UPPER-CASE CHARACTER"
        } else if (!binding.pass.text.toString().matches(".*[a-z].*".toRegex())) {
            return "MUST CONTAIN 1 LOWER-CASE CHARACTER"
        } else if (!binding.pass.text.toString().matches(".*[@#/&_].*".toRegex())) {
            return "MUST CONTAIN 1 SPECIAL CHARACTER (@#/&_)"
        } else if (!binding.pass.text.toString().matches(".*[0-9].*".toRegex())) {
            return "MUST CONTAIN 1 NUMBER CHARACTER"
        } else return null
    }

    private fun anum() {
        binding.anum.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.anumcon.helperText = validateanum()
            }
        }
    }

    private fun validateanum(): String? {

        if (binding.anum.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            if (binding.anum.text.toString().length != 12) {
                return "ADHAR NUMBER TOO SHORT"

            } else {
                return null
            }
        }
    }

    private fun email() {
        binding.email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.emailcon.helperText = validateemail()
            }
        }
    }

    private fun validateemail(): String? {
        val text = binding.email.text.toString()
        if (binding.email.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                return "INVALID EMAIL"
            } else {
                return null
            }
        }
    }



    private fun pnum() {
        binding.pnum.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.pnumcon.helperText = validatepnum()
            }
        }
    }

    private fun validatepnum(): String? {

        if (binding.pnum.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            if (binding.pnum.text.toString().length != 10) {
                return "PHONE NUMBER TOO SHORT"
            } else {
                return null
            }
        }
    }

    private fun dob() {
            if (binding.dob.text.toString().isEmpty()) {
                binding.dobcon.helperText = "REQUIRED FIELD"
            }
            else {
            binding.dobcon.helperText = null}
    }

    private fun gender() {
        binding.gender.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.gendercon.helperText = validategender()
            }
        }
    }

    private fun validategender(): String? {
        if (binding.gender.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }
    }

    private fun lname() {
        binding.lname.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.lnamecon.helperText = validatelname()
            }
        }
    }

    private fun validatelname(): String? {
        if (binding.lname.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }
    }

    private fun mname() {
        binding.mname.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.mnamecon.helperText = validatemname()

            }
        }
    }

    private fun validatemname(): String? {
        if (binding.mname.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }

    }

    private fun fname() {
        binding.fname.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.fnamecon.helperText = validatefname()
            }
        }
    }

    private fun validatefname(): String? {
        if (binding.fname.text.toString().isEmpty()) {
            return "THIS FIELD IS REQUIRED"
        } else {
            return null
        }
    }


    private fun formatDate(timestampInMillis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return sdf.format(Date(timestampInMillis))
    }

    private fun submitform(): Boolean {

        binding.passcon.helperText = validatepass()
        binding.cpasscon.helperText = validatecpass()
        binding.fnamecon.helperText = validatefname()
        binding.mnamecon.helperText = validatemname()
        binding.lnamecon.helperText = validatelname()
        binding.pnumcon.helperText = validatepnum()
        binding.anumcon.helperText = validateanum()
        binding.emailcon.helperText = validateemail()
        binding.gendercon.helperText = validategender()
        dob()

        val validfname = binding.fnamecon.helperText == null
        val validmname = binding.mnamecon.helperText == null
        val validlname = binding.lnamecon.helperText == null
        val validpnum = binding.pnumcon.helperText == null
        val validanum = binding.anumcon.helperText == null
        val validemail = binding.emailcon.helperText == null
        val validgender = binding.gendercon.helperText == null
        val validpass = binding.passcon.helperText == null
        val validcpass = binding.cpasscon.helperText == null
        val validatedob=binding.dobcon.helperText==null

        return validfname && validmname && validlname && validpnum && validanum && validemail && validgender && validpass && validcpass && validatedob

    }
}