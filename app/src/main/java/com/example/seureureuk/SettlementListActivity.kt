package com.example.seureureuk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettlementListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_list)

        val emptyStateText = findViewById<TextView>(R.id.empty_state_text)
        val addSettlementButton = findViewById<Button>(R.id.add_settlement_button)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)

        val settlements = listOf(
            Settlement("빨리디", 66000, "2024.07.29"),
            Settlement("버거킹", 54000, "2024.07.20"),
            Settlement("샐러디", 34000, "2024.07.30"),
            Settlement("써브웨이", 14000, "2024.06.30"),
            Settlement("마라탕", 34000, "2024.05.30")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.settlement_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SettlementAdapter(settlements)
        recyclerView.adapter = adapter

        if (settlements.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyStateText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateText.visibility = View.GONE
        }

        addSettlementButton.setOnClickListener {

        }

        backButton.setOnClickListener {
            finish()
        }

        bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_my -> {
                    true
                }
                else -> false
            }
        }
    }
}
