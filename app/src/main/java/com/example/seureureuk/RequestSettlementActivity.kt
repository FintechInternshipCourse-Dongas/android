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
    private lateinit var checkAgreementStatusButton: Button
    private val settlementViewModel: SettlementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_settlement)

        val participants = intent.getParcelableArrayListExtra<SettlementParticipantResponse>("participants") ?: arrayListOf()
        val settlementId = intent.getIntExtra("settlementId", -1)

        participantsRecyclerView = findViewById(R.id.participants_recycler_view)
        checkAgreementStatusButton = findViewById<Button>(R.id.check_agreement_status_button)
        participantsRecyclerView.layoutManager = LinearLayoutManager(this)

        settlementAdapter = SettlementRequestParticipationAdapter(this, participants)
        participantsRecyclerView.adapter = settlementAdapter

        checkAgreementStatusButton.setOnClickListener {
            // getSettlementParticipants 함수 연동 -> data agreementStatus 받아오기
            settlementViewModel.getSettlementParticipants(settlementId)
            settlementViewModel.getSettlementParticipantsResponse.observe(this) { response ->
                val participantList = ArrayList<SettlementParticipantResponse>()

                response.data.forEach { participant ->
                    participantList.add(participant)
                }

                val intent = Intent(this, CheckAgreementStatusActivity::class.java)
                intent.putExtra("participants", participantList)
                intent.putExtra("settlementId", settlementId)
                startActivity(intent)
                finish()
            }
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}
