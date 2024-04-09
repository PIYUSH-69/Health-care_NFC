package com.example.nfc.patient.sidenav

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class AyuCard : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var abhaName: TextInputEditText
    private lateinit var abhaNo: TextInputEditText
    private lateinit var abhaAddr: TextInputEditText
    private lateinit var abhaGender: TextInputEditText
    private lateinit var abhaDob: TextInputEditText
    private lateinit var abhaMobile: TextInputEditText
    private lateinit var photo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aabha_card)

        db = FirebaseFirestore.getInstance()
        photo = findViewById(R.id.abhaPhoto)
        abhaName = findViewById(R.id.abhaDbName)
        abhaNo = findViewById(R.id.abhaDbNo)
        abhaAddr = findViewById(R.id.abhaDbAddr)
        abhaGender = findViewById(R.id.abhaDbGender)
        abhaDob = findViewById(R.id.abhaDbDob)
        abhaMobile = findViewById(R.id.abhaDbMobile)


    }
}