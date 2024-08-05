package com.example.seureureuk

import android.os.Bundle
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

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}
