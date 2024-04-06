package com.example.nfc.patient.sidenav

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.nfc.R
import kotlinx.coroutines.runBlocking
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PatientProfile : AppCompatActivity() {

    private val REQUEST_CODE_IMAGE_UPSERT = 103
    private lateinit var ivStudentAvatar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_profile)

        ivStudentAvatar = findViewById(R.id.image_view_user_avatar)
        var avatarExists: Boolean = false

        ivStudentAvatar.setOnClickListener{
            if (!avatarExists) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, REQUEST_CODE_IMAGE_UPSERT)
            } else {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.component_image_scale_popup)
                val ivFullScale = dialog.findViewById<ImageView>(R.id.image_view_fullscale)
                ivFullScale.setImageDrawable(ivStudentAvatar.drawable)

                dialog.show()
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

    }
}