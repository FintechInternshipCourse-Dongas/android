package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FinishTransferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_finish)

        val confirmBtn = findViewById<Button>(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}