package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.ChargePointActivity.Companion.MAX_AMOUNT
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

class CertificateUserActivity : AppCompatActivity() {

    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.seureureuk.R.layout.activity_certificate_user)

        val backBtn = findViewById<ImageView>(com.example.seureureuk.R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        confirmButton = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            Toast.makeText(this, "본인인증 완료", Toast.LENGTH_SHORT).show()
        }
    }
}