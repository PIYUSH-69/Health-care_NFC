package com.example.nfc.doctors

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.nfc.R
import com.example.nfc.databinding.ActivityNewdoctorBinding
import kotlinx.coroutines.runBlocking
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class newdoctor : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivityNewdoctorBinding
    private lateinit var arrayList: ArrayList<doctorwrapper>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newdoctor)

        binding = ActivityNewdoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button8.setOnClickListener {
            val str = binding.docotr.text.toString()
            val uid = str.substring(0, 3) + System.currentTimeMillis()
            runBlocking {
                doctorwrapper.adddoctor(
                    doctorlist(
                        docuid = uid,
                        name = str,
                        domain = binding.domain.text.toString(),
                        specialization = binding.spz.text.toString(),
                        contact = binding.contact.text.toString()
                    ),
                )
            }

            MotionToast.darkColorToast(
                this, "WELCOME",
                "Doctor added Successfully",
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
            )

        }


    }


}