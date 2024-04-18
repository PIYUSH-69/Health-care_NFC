package com.example.nfc.auth

import android.content.Context
import com.auth0.jwt.JWT
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT


class Hashing {

    companion object{
        private val jwtDocumentRef = FirebaseFirestore
            .getInstance()
            .collection("credentials")
            .document("JWT_SECRET")

        suspend fun encode(userid:String): String {
            val secret: String = jwtDocumentRef
                .get()
                .await()
                .get("secret")
                .toString()

            val algorithm: Algorithm = Algorithm.HMAC256(secret)

            return JWT.create()
                .withClaim("user", userid)
                .sign(algorithm)
        }


        fun deocode(token:String): String {
            val decodedToken=JWT.decode(token)
            return decodedToken
                .getClaim("user")
                .asString()
        }




    }
}