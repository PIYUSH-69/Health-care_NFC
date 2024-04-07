package com.example.nfc.hospital

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class form_fill : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_fill)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uid=intent.extras!!.getString("uid").toString()
        val uidtxxt=findViewById<TextView>(R.id.textView13)
        uidtxxt.text=uid

        Firebase.firestore.collection("Patient").document(uid).update("qr","true")

    }
}