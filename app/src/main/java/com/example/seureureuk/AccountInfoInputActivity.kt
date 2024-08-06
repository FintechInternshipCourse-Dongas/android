package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountInfoInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info_input)

        val bankSelector = findViewById<TextView>(R.id.bank_selector)

        bankSelector.setOnClickListener {
            val bottomSheet = BankSelectionBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val accountVerificationButton = findViewById<Button>(R.id.account_verification_button)
        accountVerificationButton.setOnClickListener {
            val intent = Intent(this, AccountVerificationActivity::class.java)
            startActivity(intent)
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}
