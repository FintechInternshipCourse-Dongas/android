package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login_Btn = findViewById<Button>(com.example.seureureuk.R.id.login_button)
        login_Btn.setOnClickListener {
            val intent = Intent(this, PaymentMethodRegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}