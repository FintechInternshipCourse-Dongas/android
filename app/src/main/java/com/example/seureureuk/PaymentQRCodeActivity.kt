package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PaymentQRCodeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_qrcode)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val QRCodeImage = findViewById<ImageView>(R.id.qr_code_image)
        QRCodeImage.setOnClickListener {
            val intent = Intent(this, PaymentConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}