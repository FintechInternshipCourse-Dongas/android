package com.example.seureureuk

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.seureureuk.R.layout.activity_splash)

        Handler().postDelayed({
            //startActivity(Intent(this, SettlementListActivity::class.java))
            startActivity(Intent(this, SettlementListActivity::class.java))
            finish()
        }, 2000)
    }
}