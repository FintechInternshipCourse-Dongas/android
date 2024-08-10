package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PaymentConfirmationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)

        val settlementId = intent.getIntExtra("settlementId", -1)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SettlementDetailActivity::class.java)
            intent.putExtra("settlementId", settlementId)
            startActivity(intent)
            finish()
        }, 2000)
    }
}