package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.SettlementParticipantResponse

class RequestSettlementActivity : AppCompatActivity() {
    private lateinit var participantsRecyclerView: RecyclerView
    private lateinit var settlementAdapter: SettlementRequestParticipationAdapter
    private lateinit var paymentQRButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_settlement)
        
        val participants = intent.getParcelableArrayListExtra<SettlementParticipantResponse>("participants") ?: arrayListOf()

        participantsRecyclerView = findViewById(R.id.participants_recycler_view)
        paymentQRButton = findViewById(R.id.payment_qr_button)
        participantsRecyclerView.layoutManager = LinearLayoutManager(this)

        settlementAdapter = SettlementRequestParticipationAdapter(this, participants)
        participantsRecyclerView.adapter = settlementAdapter

        if (participants.all { it.agreementStatus }) {
            paymentQRButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.consent_complete))
        } else {
            paymentQRButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.consent_waiting))
        }

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
