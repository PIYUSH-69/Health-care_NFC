package com.example.nfc.patient.sidenav

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nfc.R
import com.example.nfc.hospital.patientdata.medicalReport.ShowReport
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlin.collections.HashMap


class MedicalReports : AppCompatActivity() {

    lateinit var choose_img: ImageButton
    lateinit var upload_img: ImageButton
    lateinit var retrieve_img: ImageButton
    lateinit var image_view: ImageView
    lateinit var title: TextInputEditText
    var fileUri: Uri? = null
    lateinit var userId: String // User's unique ID
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_reports)

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid ?: ""

        choose_img = findViewById(R.id.choose_image)
        upload_img = findViewById(R.id.upload_image)
        retrieve_img = findViewById(R.id.retrieve_image)
        image_view = findViewById(R.id.image_view)
        title = findViewById(R.id.enter_title)

        choose_img.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Images to Upload"), 0
            )
        }

        upload_img.setOnClickListener {
            if (fileUri != null) {
                uploadImage()
            } else {
                Toast.makeText(
                    applicationContext, "Please Select Image to Upload",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //Show All Images Function
        retrieve_img.setOnClickListener {
            val intent = Intent(applicationContext, ShowReport::class.java)
            startActivity(intent)
            //retrive_image()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
                image_view.setImageBitmap(bitmap)

            } catch (e: Exception) {
                Log.e("Exception", "Error: " + e)
            }
        }
    }


    fun uploadImage() {
        if (fileUri != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading Image...")
            progressDialog.setMessage("Processing...")
            progressDialog.show()

            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(userId).child(title.text.toString())
            ref.putFile(fileUri!!).addOnSuccessListener { uploadTask ->
                // Get the download URL from the storage task
                uploadTask.storage.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    progressDialog.dismiss()
                            Toast.makeText(
                                applicationContext,
                                "File Uploaded Successfully",
                                Toast.LENGTH_LONG
                            ).show()

                    // Add image data to Firestore
//                    val db = FirebaseFirestore.getInstance()
//                    val imageInfo = HashMap<String, Any>()
//                    imageInfo["title"] = title.text.toString()
//                    imageInfo["imageUrl"] = imageUrl
//                    db.collection("images").add(imageInfo)
//                        .addOnSuccessListener { documentReference ->
//                            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
//                            progressDialog.dismiss()
//                            Toast.makeText(
//                                applicationContext,
//                                "File Uploaded Successfully",
//                                Toast.LENGTH_LONG
//                            )
//                                .show()
//                        }.addOnFailureListener { e ->
//                            Log.w("TAG", "Error adding document", e)
//                            progressDialog.dismiss()
//                            Toast.makeText(
//                                applicationContext,
//                                "File Upload Failed...",
//                                Toast.LENGTH_LONG
//                            )
//                                .show()
//                        }
//                }.addOnFailureListener { e ->
//                    Log.d("TAG", "Error getting download URL", e)
//                    progressDialog.dismiss()
//                    Toast.makeText(applicationContext, "File Upload Failed...", Toast.LENGTH_LONG)
//                        .show()
//                }
//            }.addOnFailureListener { e ->
//                progressDialog.dismiss()
//                Toast.makeText(applicationContext, "File Upload Failed...", Toast.LENGTH_LONG)
//                    .show()
//            }
                }
            }
        }
    }
}