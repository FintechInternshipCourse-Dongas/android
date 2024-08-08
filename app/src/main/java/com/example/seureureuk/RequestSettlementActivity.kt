package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RequestSettlementActivity : AppCompatActivity() {

    private lateinit var participantsRecyclerView: RecyclerView
    private lateinit var settlementAdapter: SettlementRequestParticipationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_settlement)

        participantsRecyclerView = findViewById(R.id.participants_recycler_view)
        participantsRecyclerView.layoutManager = LinearLayoutManager(this)

        val members = listOf(
            com.example.seureureuk.data.model.SettlementParticipation("민선", "5000", true),
            com.example.seureureuk.data.model.SettlementParticipation("가온", "4500", false),
            com.example.seureureuk.data.model.SettlementParticipation("해성", "7000", true),
            com.example.seureureuk.data.model.SettlementParticipation("나영", "3000", false),
            com.example.seureureuk.data.model.SettlementParticipation("한비", "6000", true),
            com.example.seureureuk.data.model.SettlementParticipation("건", "4000", false)
        )

        settlementAdapter = SettlementRequestParticipationAdapter(this, members)
        participantsRecyclerView.adapter = settlementAdapter

        val paymentQRButton: Button = findViewById(R.id.payment_qr_button)
        paymentQRButton.setOnClickListener {
            val intent = Intent(this, PaymentQRCodeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}
