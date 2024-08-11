package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.ui.viewmodel.GroupViewModel
import com.example.seureureuk.ui.viewmodel.SettlementViewModel

class SettlementDetailActivity: AppCompatActivity() {

    private val settlementViewModel: SettlementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_detail)

        val settlementId = intent.getIntExtra("settlementId", -1)

        settlementViewModel.getSettlementDetail(settlementId)
        settlementViewModel.getSettlementDetailResponse.observe(this) { response ->
            val settlementNameTextView = findViewById<TextView>(R.id.settlement_name)
            settlementNameTextView.text = response.data.settlementName

            val totalAmountTextView = findViewById<TextView>(R.id.total_amount)
            totalAmountTextView.text = response.data.totalAmount.toString()

            val meetingDateLabel: TextView = findViewById<View>(R.id.meeting_date_row).findViewById(R.id.label)
            val meetingDateValue: TextView = findViewById<View>(R.id.meeting_date_row).findViewById(R.id.value)
            meetingDateLabel.text = "모임 날짜:"
            meetingDateValue.text = response.data.groupingAt

            val settlementDateLabel: TextView = findViewById<View>(R.id.settlement_date_row).findViewById(R.id.label)
            val settlementDateValue: TextView = findViewById<View>(R.id.settlement_date_row).findViewById(R.id.value)
            settlementDateLabel.text = "정산 날짜:"
            settlementDateValue.text = response.data.settlementAt

            val locationLabel: TextView = findViewById<View>(R.id.location_row).findViewById(R.id.label)
            val locationValue: TextView = findViewById<View>(R.id.location_row).findViewById(R.id.value)
            locationLabel.text = "모임 장소:"
            locationValue.text = response.data.settlementPlace

            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = SettlementDetailParticipationAdapter(this, response.data.participants)
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
            val intent = Intent(this, SettlementListActivity::class.java)
            startActivity(intent)
        }
    }

}