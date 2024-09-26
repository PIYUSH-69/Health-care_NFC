package com.example.nfc.patient

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.BuildConfig
import com.example.nfc.R
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class chatbot : AppCompatActivity() {
    private val apikey = BuildConfig.apiKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatbot)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val symptoms = findViewById<TextInputEditText>(R.id.symptoms)
        val text = findViewById<TextView>(R.id.textView22)
        val button = findViewById<Button>(R.id.button14)
        val card = findViewById<CardView>(R.id.symptomcard)

        button.setOnClickListener {
            card.visibility = View.VISIBLE
            val symp = symptoms.text.toString()
            text.text = "WAIT FOR 5 to 10 Seconds"
            val generativeModel = GenerativeModel(
                // For text-only input, use the gemini-pro model
                modelName = "gemini-pro",
                // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                apiKey = apikey
            )
            val prompt = "I have this symptoms give me diagnosis ,home remedy,actions: $symp"

            MainScope().launch {
                try {
                    val response = generativeModel.generateContent(prompt)
                    Log.d("Chatbot", "Response received")
                    val string = response.text
                    val newString = string!!.replace("*", "")
                    text.text = newString
                    Log.d("Chatbot", "UI updated")
                } catch (e: Exception) {
                    Log.e("Chatbot", "Error occurred: ${e.message}")
                    text.text = "An error occurred: ${e.message}"
                }
            }
        }
    }

}