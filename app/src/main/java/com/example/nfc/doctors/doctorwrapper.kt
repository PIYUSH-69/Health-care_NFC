package com.example.nfc.doctors

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

    suspend fun adddoctor(docotrobject: doctorlist) {
        val doctorId: String = docotrobject.name.toString()
        doctorCollectionRef
            .document(doctorId)
            .set(docotrobject)
            .await()
    }

}


}
