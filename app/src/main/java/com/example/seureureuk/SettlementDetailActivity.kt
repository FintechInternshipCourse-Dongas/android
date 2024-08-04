package com.example.seureureuk

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettlementDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_detail)

        val meetingDateLabel: TextView = findViewById<View>(R.id.meeting_date_row).findViewById(R.id.label)
        val meetingDateValue: TextView = findViewById<View>(R.id.meeting_date_row).findViewById(R.id.value)
        meetingDateLabel.text = "모임 날짜:"
        meetingDateValue.text = "2024.07.29"

        val settlementDateLabel: TextView = findViewById<View>(R.id.settlement_date_row).findViewById(R.id.label)
        val settlementDateValue: TextView = findViewById<View>(R.id.settlement_date_row).findViewById(R.id.value)
        settlementDateLabel.text = "정산 날짜:"
        settlementDateValue.text = "2024.07.29"

        val locationLabel: TextView = findViewById<View>(R.id.location_row).findViewById(R.id.label)
        val locationValue: TextView = findViewById<View>(R.id.location_row).findViewById(R.id.value)
        locationLabel.text = "모임 장소:"
        locationValue.text = "샐러디 사당점"

        // Sample data
        val members = listOf(
            SettlementParticipation("민선", "11,000원"),
            SettlementParticipation("가은", "12,000원"),
            SettlementParticipation("해성", "13,000원"),
            SettlementParticipation("나영", "14,000원")
        )

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettlementParticipationAdapter(members)
    }

}