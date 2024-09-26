package com.example.nfc.patient

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nfc.R
import com.example.nfc.auth.Register
import com.example.nfc.patient.appointments.Appointments
import com.example.nfc.patient.qr.nfc
import com.example.nfc.patient.sidenav.AyuCard
import com.example.nfc.patient.sidenav.MedicalReports
import com.example.nfc.patient.sidenav.PatientProfile
import com.example.nfc.patient.sidenav.Records
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class Patient_main : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_main)
        setSupportActionBar(findViewById(R.id.toolbars))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navHeaderView = navView.getHeaderView(0)
        val logout = findViewById<Button>(R.id.logoutpatient)
        val form = findViewById<ImageView>(R.id.button4)
        navView.itemIconTintList = null
        val navHeaderImage: ImageView = navHeaderView.findViewById(R.id.nav_header_image)
        val navHeaderName: TextView = navHeaderView.findViewById(R.id.nav_header_name)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tollfree = findViewById<Button>(R.id.call)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        tollfree.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 14477))
            startActivity(intent)

        }

        val text = findViewById<TextView>(R.id.textView)

        fab.setOnClickListener {
            startActivity(Intent(this, chatbot::class.java))

        }

        val userid = Firebase.auth.currentUser!!.uid
        runBlocking {
            patientcrud.getphotourl(userid) {
                Glide.with(applicationContext)
                    .setDefaultRequestOptions(RequestOptions())
                    .load(it)
                    .into(navHeaderImage)
            }
        }
        Firebase.firestore.collection("Patient").document(userid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fname = document.getString("FIRST_NAME")
                    val lname = document.getString("LAST_NAME")
                    navHeaderName.text = fname + " " + lname
                    text.text = "WELCOME " + fname

                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data!!.get("1")}")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


        logout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("counter", MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putBoolean("flag", false)
            }.apply()
            startActivity(Intent(this, Register::class.java))
        }

        form.setOnClickListener {
            startActivity(Intent(this, nfc::class.java))
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Appointments -> {
                    val aIntent = Intent(this, Appointments::class.java)
                    startActivity(aIntent)
                }

                R.id.record -> {
                    val rIntent = Intent(this, Records::class.java)
                    startActivity(rIntent)
                }

                R.id.uploadodoc -> {
                    val uIntent = Intent(this, MedicalReports::class.java)
                    startActivity(uIntent)
                }

                R.id.profile -> {
                    val pIntent = Intent(this, PatientProfile::class.java)
                    startActivity(pIntent)
                }

                R.id.ayucard -> {
                    val yIntent = Intent(this, AyuCard::class.java)
                    startActivity(yIntent)
                }

                R.id.sos -> {
                    val alertDialog = AlertDialog.Builder(this@Patient_main)
                    alertDialog.setMessage("Call Emergency?")
                    alertDialog.setPositiveButton("YES") { _, _ ->
                        // Check if permission is not granted
                        if (ContextCompat.checkSelfPermission(
                                this@Patient_main,
                                Manifest.permission.CALL_PHONE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            // Permission has already been granted
                            val callSos = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 102))
                            startActivity(callSos)
                        } else {
                            // Request permission
                            ActivityCompat.requestPermissions(
                                this@Patient_main,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                100
                            )
                        }

                    }
                    alertDialog.setNegativeButton("NO") { _, _ ->

                    }
                    alertDialog.create().show()
                }
            }
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, make the phone call
                    val callSos = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 102))
                    startActivity(callSos)
                } else {
                    // Permission denied
                    Toast.makeText(
                        this@Patient_main,
                        "Permission denied to make phone calls",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}