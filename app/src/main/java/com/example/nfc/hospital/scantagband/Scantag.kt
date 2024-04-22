package com.example.nfc.hospital.scantagband

import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.NfcF
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.example.nfc.hospital.Hospital_main
import com.example.nfc.hospital.nfchospital.form_fill
import com.example.nfc.hospital.nfchospital.qrscanner_hospital
import java.lang.Exception

class scantag : AppCompatActivity() {

    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    private var pendingIntent: PendingIntent? = null
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scantag)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //nfc
        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE)
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndef.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
        intentFiltersArray = arrayOf(ndef)
        if (nfcAdapter == null) {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert")
            builder.setMessage("This device doesn't support NFC.")
            builder.setPositiveButton("OK"){_,_ ->startActivity(
                Intent(this,
                    Hospital_main::class.java)
            )}
            builder.show()

        } else if (!nfcAdapter!!.isEnabled) {
            val builder = AlertDialog.Builder(this, R.style.Theme_NFC)
            builder.setTitle("NFC Disabled")
            builder.setMessage("Plesae Enable NFC")
            builder.setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    var userid ="";
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(ContentValues.TAG, "NFC READ initalized STAGE 1")

        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            with(parcelables) {
                try {
                    Log.d(ContentValues.TAG, "NFC READ initalized")
                    Toast.makeText(this@scantag, "SUCCCESS", Toast.LENGTH_SHORT).show()
                    val inNdefMessage = this?.get(0) as NdefMessage
                    val inNdefRecords = inNdefMessage.records
                    //if there are many records, you can call inNdefRecords[1] as array
                    var ndefRecord_0 = inNdefRecords[0]
                    var inMessage = String(ndefRecord_0.payload)

                    userid = inMessage.drop(3)
                    startActivity(Intent(this@scantag, recordoptions::class.java).putExtra("uid",userid))

                } catch (ex: Exception) {
                    Log.d(ContentValues.TAG, "NFC READ initalized STAGE ERROR"+ex)
                    Toast.makeText(applicationContext, "There are no Machine and Shop information found!, please click write data to write those!", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else
        {
            Log.d(ContentValues.TAG, "NFC READ initalized STAGE ERROR else")
        }
    }

    override fun onPause() {
        if (this.isFinishing) {
            nfcAdapter?.disableForegroundDispatch(this)
        }
        super.onPause()
    }
}

