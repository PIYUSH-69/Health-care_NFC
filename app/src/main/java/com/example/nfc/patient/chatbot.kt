package com.example.nfc.patient

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nfc.R
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class chatbot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatbot)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val symptoms=findViewById<TextInputEditText>(R.id.symptoms)
        val text=findViewById<TextView>(R.id.textView22)
        val button=findViewById<Button>(R.id.button14)

        button.setOnClickListener {
            val symp=symptoms.text.toString()
            val generativeModel = GenerativeModel(
                // For text-only input, use the gemini-pro model
                modelName = "gemini-pro",
                // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                apiKey = "AIzaSyAHdL1s-swckDUAZPrN2_fd401N4PT14BQ"
            )
            val prompt = "I have this symptoms give me diagnosis ,home remedy,actions: $symp"

            MainScope().launch {
                val response = generativeModel.generateContent(prompt)


                text.setText(response.text)
            }
        }


    }

}