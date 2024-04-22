package com.example.nfc.hospital.nfchospital

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
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
import com.example.nfc.auth.Hashing
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class nfc_tag_hospital : AppCompatActivity() {


    private lateinit var hashcode:String
    private lateinit var Text: TextView
    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    private var pendingIntent: PendingIntent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nfc_tag_hospital)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Text=findViewById(R.id.textView25)

        val uid=intent.extras!!.getString("userid").toString()
        hashcode= uid

        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndef.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
        intentFiltersArray = arrayOf(ndef)
        if (nfcAdapter == null) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("This device doesn't support NFC.")
            builder.setPositiveButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(false)
            myDialog.show()

        } else if (!nfcAdapter!!.isEnabled) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("NFC Disabled")
            builder.setMessage("Plesae Enable NFC")
            builder.setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
            builder.setNegativeButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(false)
            myDialog.show()

        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)


        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {

            val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            with(parcelables) {
                try {

                    //write operation
                        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action
                            || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action
                        ) {

                            val tag =
                                intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return
                            val ndef = Ndef.get(tag) ?: return

                            if (ndef.isWritable) {

                                var message = NdefMessage(
                                    arrayOf(
                                        NdefRecord.createTextRecord("en", hashcode)
                                    )
                                )
                                try {
                                    ndef.connect()
                                    ndef.writeNdefMessage(message)
                                    ndef.close()
                                }catch(ex: Exception){
                                    Toast.makeText(
                                        applicationContext,
                                        "WRITE UNSUCCESFUUL",
                                        Toast.LENGTH_SHORT
                                    )
                                }

                                Text.setText("USER ID: "+ hashcode.toString());
                                Toast.makeText(
                                    applicationContext,
                                    "Successfully Wroted!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }else{
                                Log.d(TAG, "NFC READ NOT WRITABLE")

                            }
                        }
                        else{
                            Log.d(TAG, "NFC READ ERROORRRRR")
                        }
//
                } catch (ex: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "There are no Machine and Shop information found!, please click write data to write those!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onPause() {
        if (this.isFinishing) {
            nfcAdapter?.disableForegroundDispatch(this)
        }
        super.onPause()
}


}