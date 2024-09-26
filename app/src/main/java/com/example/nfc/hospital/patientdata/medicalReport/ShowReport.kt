package com.example.nfc.hospital.patientdata.medicalReport

import ImageAdapterString
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference


class ShowReport : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    var progressBar: ProgressBar? = null
    var adapter: ImageAdapterString? = null
    lateinit var userId: String
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showallimages)
        var imagelist = ArrayList<Photos>()
        recyclerView = findViewById(R.id.recyclerview)
        adapter = ImageAdapterString(imagelist, this)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        progressBar = findViewById(R.id.progress)
        progressBar!!.visibility = View.VISIBLE

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid ?: ""

        val image_refrance: StorageReference = FirebaseStorage.getInstance().reference.child(userId)

        image_refrance.listAll().addOnSuccessListener(OnSuccessListener<ListResult> { listResult ->
            for (file in listResult.items) {
                file.getDownloadUrl()
                    .addOnSuccessListener { uri -> // adding the url in the arraylist
                        var bb = file.name
                        imagelist.add(Photos(bb, uri.toString()))
                        Log.d("Itemvalue", bb + uri.toString())
                    }.addOnSuccessListener {
                        recyclerView!!.setAdapter(adapter)
                        progressBar!!.setVisibility(View.GONE)
                    }
            }
        })
    }
}
