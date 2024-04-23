package com.example.nfc.patient.qr

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.auth.Hashing
import com.example.nfc.patient.Patient_main
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.runBlocking

class qrcodepatient : AppCompatActivity() {

    private lateinit var bmpQr1 : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_qrcodepatient)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userid=Firebase.auth.currentUser!!.uid
        val qr =findViewById<ImageView>(R.id.qr_code)
        val encodedtoken= runBlocking { Hashing.encode(userid)}
        Log.d(TAG, "onCreate: "+encodedtoken)
        bmpQr1=qrcodegenerator(encodedtoken)
        qr.setImageBitmap(bmpQr1)
        qrcheck()

    }

    private fun qrcheck() {

        val userid=Firebase.auth.currentUser!!.uid
        val db = Firebase.firestore
        val docRef = db.collection("Patient").document(userid)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val a=snapshot.data!!.get("qr")
                Log.d(TAG, "Current data: ${snapshot.data}")
                Log.d(TAG, "Current : $a")
                if (a=="true"){
                    startActivity(Intent(this,Patient_main::class.java))
                    Toast.makeText(this, "FORM SUBMITTED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                    Firebase.firestore.collection("Patient").document(userid).update("qr","false")

                }
            } else {
                Log.d(TAG, "Current data: null")
            }
        }


//        val userid=Firebase.auth.currentUser!!.uid
//        Firebase.firestore.collection("Patient").document(userid)
//            .get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
//                    val qr=document.getString("qr")
//                    if (qr=="true"){
//                        startActivity(Intent(this,Patient_main::class.java))
//                        Toast.makeText(this, "FORM SUBMITTED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
//                    }
//                    Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data!!.get("1")}")
//                } else {
//                    Log.d(ContentValues.TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(ContentValues.TAG, "get failed with ", exception)
//            }
    }


    private fun qrcodegenerator(string: String): Bitmap {
        val writer= QRCodeWriter()
        val bitMatrix: BitMatrix = writer.encode(string, BarcodeFormat.QR_CODE, 1024, 1024)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bmp

    }
}