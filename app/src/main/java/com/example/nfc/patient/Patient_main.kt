package com.example.nfc.patient

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.nfc.R
import com.example.nfc.auth.Register
import com.example.nfc.patient.qr.nfc
import com.example.nfc.patient.sidenav.Appointments
import com.example.nfc.patient.sidenav.AyuCard
import com.example.nfc.patient.sidenav.MedicalReports
import com.example.nfc.patient.sidenav.PatientProfile
import com.example.nfc.patient.sidenav.Records
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Patient_main : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_main)
        setSupportActionBar(findViewById(R.id.toolbars))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val navHeaderView = navView.getHeaderView(0)
        val logout=findViewById<Button>(R.id.logoutpatient)
        val form=findViewById<Button>(R.id.button4)
        navView.itemIconTintList = null
        val navHeaderImage: ImageView = navHeaderView.findViewById(R.id.nav_header_image)
        val navHeaderName: TextView = navHeaderView.findViewById(R.id.nav_header_name)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userid=Firebase.auth.currentUser!!.uid
        val db= Firebase.firestore.collection("Patient").document(userid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val fname=document.getString("FIRST NAME")
                    val lname=document.getString("LAST NAME")
                    navHeaderName.text=fname +" "+lname
                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data!!.get("1")}")
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


        logout.setOnClickListener {
            val sharedPreferences= getSharedPreferences("counter", MODE_PRIVATE)
            sharedPreferences.edit().apply{
                putBoolean("flag",false) }.apply()
            startActivity(Intent(this, Register::class.java))
        }

        form.setOnClickListener {
            startActivity(Intent(this, nfc ::class.java))
        }

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
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
            }
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}