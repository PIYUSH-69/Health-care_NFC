package com.example.nfc.patient.appointments

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivitySubmitappointmentBinding
import com.example.nfc.doctors.doctorlist
import com.example.nfc.doctors.doctorwrapper
import com.example.nfc.patient.Patient_main
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import kotlinx.coroutines.runBlocking
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.Calendar
import java.util.Calendar.YEAR
import java.util.Date
import java.util.Locale

class SubmitAppointment : AppCompatActivity() {
    private lateinit var binding: ActivitySubmitappointmentBinding
    private lateinit var doctors: doctorlist
    private lateinit var appointments: appointmentwrapper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_submitappointment)
        binding=ActivitySubmitappointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val docuid=intent.extras!!.getString("Doctorid").toString()
        Log.d(TAG, "onCreate doctorid: "+docuid)
       doctors= runBlocking { doctorwrapper.getdocotr(docuid) }!!

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val array = arrayListOf(
            "Morning  :10 am-1 pm",
            "Afternoon:2 pm-5 pm ",
            "Evening  :6 pm-10 pm")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        val spinner=findViewById<AutoCompleteTextView>(R.id.slot)
        spinner.setAdapter(adapter)

        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)


        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()

        datePickerBuilder.setCalendarConstraints(
            CalendarConstraints.Builder()
                .setStart(Calendar.getInstance().timeInMillis)
                // .setEnd(Calendar.getInstance().apply {set(YEAR, 2025)  }.timeInMillis)
                //.setStart(today.timeInMillis)
                .setOpenAt(today.timeInMillis)
                .setEnd(Calendar.getInstance().timeInMillis+ 5)
                .build()
        )



        val datePicker = datePickerBuilder.build()


        val dob=findViewById<TextInputEditText>(R.id.dateob)


        dob.setOnClickListener {
            if (!datePicker.isAdded) {
                datePicker.show(supportFragmentManager, "DatePicker")
            }
        }
        datePicker.addOnPositiveButtonClickListener { selection ->
            // Format the selected date from the Unix timestamp
            val selectedDateInMillis = selection as Long
            val formattedDate = formatDate(selectedDateInMillis)
            dob.setText(formattedDate)
        }

       binding.doctorrr.setText(doctors.name)
        binding.button12.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_CODE)
            } else {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctors.contact))
                startActivity(intent)
            }
        }


        val user=Firebase.auth.currentUser!!.uid
        val db=Firebase.firestore.collection("Appointments").document(user)

        binding.button11.setOnClickListener {
            appointments=appointmentwrapper(
                booktime = System.currentTimeMillis().toString(),
                doctorid =  docuid,
                timeslot = binding.slot.text.toString() ,
                date =binding.dateob.text.toString()
            )

            db.set(appointments).addOnSuccessListener {
                startActivity(Intent(this,Patient_main::class.java))
                MotionToast.darkColorToast(this,"GET WELL SOON",
                    "Appointment Booked Successfully",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))

            }
        }
    }

    private fun formatDate(timestampInMillis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return sdf.format(Date(timestampInMillis))
    }
}