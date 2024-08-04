package com.example.seureureuk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettlementListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_list)

        // View 초기화
        val settlementLayout = findViewById<LinearLayout>(R.id.settlement_layout)
        val emptyStateText = findViewById<TextView>(R.id.empty_state_text)
        val addSettlementButton = findViewById<Button>(R.id.add_settlement_button)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val membersTitleLayout = findViewById<LinearLayout>(R.id.members_title_layout)
        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)

        // 예제 데이터 설정
        val settlements = listOf(
            Settlement("빨리디", 66000, "2024.07.29"),
            Settlement("버거킹", 54000, "2024.07.20")
            // 더 많은 아이템을 추가하거나 빈 리스트로 테스트 가능
        )

        // 정산 내역 표시
        if (settlements.isEmpty()) {
            showEmptyState(settlementLayout, emptyStateText)
        } else {
            showSettlementList(settlementLayout, emptyStateText, settlements)
        }

        // 정산 추가 버튼 클릭 처리
        addSettlementButton.setOnClickListener {
            // 정산 추가 기능 구현 (예: 새로운 화면으로 이동)
        }

        // 뒤로가기 버튼 클릭 처리
        backButton.setOnClickListener {
            finish() // 이전 화면으로 돌아가기
        }

        // Bottom Navigation 처리 (예: 클릭 이벤트 추가)
        bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Home 선택 시 처리할 작업
                    true
                }
                R.id.navigation_my -> {
                    // My 선택 시 처리할 작업
                    true
                }
                else -> false
            }
        }
    }

    private fun showEmptyState(settlementLayout: LinearLayout, emptyStateText: TextView) {
        settlementLayout.visibility = View.GONE
        emptyStateText.visibility = View.VISIBLE
    }

    private fun showSettlementList(
        settlementLayout: LinearLayout,
        emptyStateText: TextView,
        settlements: List<Settlement>
    ) {
        settlementLayout.visibility = View.VISIBLE
        emptyStateText.visibility = View.GONE

        // 정산 내역 추가 - RecyclerView를 사용하는 것이 좋습니다.
        for (settlement in settlements) {
            val view = layoutInflater.inflate(R.layout.settlement_item, settlementLayout, false)
            // 정산 내역 데이터를 뷰에 바인딩
            view.findViewById<TextView>(R.id.transaction_name).text = settlement.name
            view.findViewById<TextView>(R.id.transaction_amount).text = "${settlement.amount}원"
            view.findViewById<TextView>(R.id.transaction_date).text = settlement.date
            settlementLayout.addView(view)
        }
    }
}

// 정산 내역 데이터 클래스
data class Settlement(val name: String, val amount: Int, val date: String)
