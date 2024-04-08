package com.example.nfc.patient

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class patientwrapper (
    val FIRST_NAME: String? = null,
    val MIDDLE_NAME: String? = null,
    val LAST_NAME: String? = null,
    val GENDER: String? = null,
    val DOB: String? = null,
    val PHONE_NUMBER: String? = null,
    val ADHARCARD_NUMBER: String? = null,
    val EMAIL: String? = null,
    val WEIGHT :String? = null,
    val BLOOD_GROUP :String? = null,
    val BLOOD_PRESSURE :String? = null,
    val DIABETES :String? = null,
    val ASTHAMA :String? = null,
    val SURGERIES :String? = null,
    val Allergies :String? = null,
    val AYUSHMAN_ID :String? = null,
)

class patientcrud {

    companion object {
        private val patientCollectionRef = FirebaseFirestore.getInstance().collection("Patient")
        suspend fun getpatient(userId: String): patientwrapper? {
            val document= patientCollectionRef.document(userId).get().await()
            Log.d(ContentValues.TAG, "DocumentSnapshot data:wrapper"+ document.toObject(patientwrapper::class.java)!!)
            return document.toObject(patientwrapper::class.java)!!
        }

        suspend fun getphotourl(userId: String): String{
            val imageurl=""
            return imageurl
        }

    }    }


