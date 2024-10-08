package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DeleteGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val userInfoBtn = findViewById<Button>(R.id.confirm_button)
        userInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPageGroupActivity::class.java)
            startActivity(intent)
        }
    }
}