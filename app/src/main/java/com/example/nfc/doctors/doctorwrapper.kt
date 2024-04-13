package com.example.nfc.doctors

import com.example.nfc.patient.patientcrud
import com.example.nfc.patient.patientwrapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class doctorlist (
    val docuid:String?=null,
    val name :String? = null,
    val domain :String? = null,
    val specialization :String? = null,
    val contact :String? = null,)

class doctorwrapper{

companion object{
    private val doctorCollectionRef = FirebaseFirestore.getInstance().collection("Doctors")


    suspend fun getdocotr(userId: String): doctorlist? {
        val document = doctorCollectionRef.document(userId).get().await()
        return document.toObject(doctorlist::class.java)!!
    }

    suspend fun adddoctor(docotrobject: doctorlist) {
        val doctorId: String = docotrobject.docuid.toString()
        doctorCollectionRef
            .document(doctorId)
            .set(docotrobject)
            .await()
    }

}


}
