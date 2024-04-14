package com.example.nfc.hospital

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.nfc.R
import com.example.nfc.auth.Register
import com.example.nfc.doctors.Doctors
import com.example.nfc.hospital.hospitalsidenav.HospitalAppointments
import com.example.nfc.hospital.nfchospital.NFC_hospital
import com.example.nfc.hospital.patientdata.PatientData
import com.google.android.material.navigation.NavigationView

class Hospital_main : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_main)
        setSupportActionBar(findViewById(R.id.toolbars))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val navHeaderView = navView.getHeaderView(0)
        val logout=findViewById<Button>(R.id.logout)

        navView.itemIconTintList = null
        val navHeaderImage: ImageView = navHeaderView.findViewById(R.id.nav_header_image)
        val navHeaderName: TextView = navHeaderView.findViewById(R.id.nav_header_name)
        val navHeaderdesc: TextView = navHeaderView.findViewById(R.id.nav_header_description)

        navHeaderName.text="HOSPITAL"
        navHeaderdesc.text=""

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val scan=findViewById<Button>(R.id.button6)
        scan.setOnClickListener {
            startActivity(Intent(this, NFC_hospital::class.java))
        }

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Appointments -> {
                    val eIntent = Intent(this, HospitalAppointments::class.java)
                    startActivity(eIntent)
                }
                R.id.Doctors -> {
                    val favEventIntent = Intent(this, Doctors::class.java)
                    startActivity(favEventIntent)
                }

                R.id.Patient_data -> {
                    val cIntent = Intent(this, PatientData::class.java)
                    startActivity(cIntent)
                }
            }
            true
        }

        logout.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}