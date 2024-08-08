package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FinishChargeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.seureureuk.R.layout.activity_charge_finish)

        val confirmBtn = findViewById<Button>(com.example.seureureuk.R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val intent = Intent(this, com.example.seureureuk.MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}