package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val userInfoBtn = findViewById<Button>(R.id.manage_info_button)
        userInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPageUserInfoActivity::class.java)
            startActivity(intent)
        }

        val sendBtn = findViewById<Button>(R.id.send_button)
        sendBtn.setOnClickListener {
            val intent = Intent(this, BankTransferActivity::class.java)
            startActivity(intent)
        }

        val rechargeBtn = findViewById<Button>(R.id.recharge_button)
        rechargeBtn.setOnClickListener {
            val intent = Intent(this, ChargePointActivity::class.java)
            startActivity(intent)
        }

        val paymentInfoBtn = findViewById<ImageView>(R.id.payment_management_arrow)
        paymentInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPagePaymentActivity::class.java)
            startActivity(intent)
        }
        val paymentLayoutButton: RelativeLayout = findViewById(R.id.payment_management)
        paymentLayoutButton.setOnClickListener {
            val intent = Intent(this, MyPagePaymentActivity::class.java)
            startActivity(intent)
        }


        val groupInfoBtn = findViewById<ImageView>(R.id.group_management_arrow)
        groupInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPageGroupActivity::class.java)
            startActivity(intent)
        }
        val groupLayoutButton: RelativeLayout = findViewById(R.id.group_management)
        groupLayoutButton.setOnClickListener {
            val intent = Intent(this, MyPagePaymentActivity::class.java)
            startActivity(intent)
        }
    }
}