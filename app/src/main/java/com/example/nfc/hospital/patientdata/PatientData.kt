package com.example.nfc.hospital.patientdata

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nfc.R
import com.example.nfc.patient.patientwrapper
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PatientData : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    private lateinit var usersArrayList: ArrayList<patientwrapper>
    private lateinit var myAdapter: PatientDataAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var filteredUsersList: ArrayList<patientwrapper>
    private lateinit var searchView: SearchView
    private lateinit var usersCollection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_data)
        recycler = findViewById(R.id.userRecyclerView)
        searchView = findViewById(R.id.searchUsers)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        usersArrayList = arrayListOf()
        filteredUsersList = arrayListOf()

        myAdapter = PatientDataAdapter(filteredUsersList, this)
        recycler.adapter = myAdapter

        db = FirebaseFirestore.getInstance()
        usersCollection = db.collection("Patient")

        setupSearchView()
        EventChangeListener()
    }

    private fun EventChangeListener() {
        usersCollection.addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firestore Error", error.message.toString())
                return@addSnapshotListener
            }
            usersArrayList.clear()
            for (doc in value!!) {
                val addPatient = doc.toObject(patientwrapper::class.java)
                usersArrayList.add(addPatient)
            }
            filter("") // Initially, display all users
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return true
            }
        })
    }

    private fun filter(query: String) {
        filteredUsersList.clear()
        for (user in usersArrayList) {
            if (user.FIRST_NAME?.contains(query, true) == true ||
                user.MIDDLE_NAME?.contains(query, true) == true ||
                user.LAST_NAME?.contains(query, true) == true ||
                user.PHONE_NUMBER?.contains(query, true) == true ||
                user.ADHARCARD_NUMBER?.contains(query, true) == true) {
                filteredUsersList.add(user)
            }
        }
        myAdapter.notifyDataSetChanged()
    }
}