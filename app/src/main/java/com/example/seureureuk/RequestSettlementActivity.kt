package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.SettlementParticipantResponse
import com.example.seureureuk.ui.viewmodel.SettlementViewModel

class RequestSettlementActivity : AppCompatActivity() {
    private lateinit var participantsRecyclerView: RecyclerView
    private lateinit var settlementAdapter: SettlementRequestParticipationAdapter
    private lateinit var paymentQRButton: Button
    private val settlementViewModel: SettlementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_settlement)

        val participants = intent.getParcelableArrayListExtra<SettlementParticipantResponse>("participants") ?: arrayListOf()
        val settlementId = intent.getIntExtra("settlementId", -1)

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
            // 넘어가기 전에 정산(결제) api 연동
            settlementViewModel.executeSettlementProcess(settlementId)

            settlementViewModel.executeSettlementProcessResponse.observe(this) { response ->
                if (response.data.settlementStatus == "SUCCESS") {
                    val intent = Intent(this, PaymentQRCodeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            settlementViewModel.error.observe(this) { errorMessage ->
                Log.e("RequestSettlementActivity", errorMessage)
            }
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}
