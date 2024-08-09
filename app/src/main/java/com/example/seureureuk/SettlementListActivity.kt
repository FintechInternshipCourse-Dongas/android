package com.example.seureureuk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettlementListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_list)

        val settlements = listOf(
            com.example.seureureuk.data.model.Settlement("빨리디", 66000, "2024.07.29"),
            com.example.seureureuk.data.model.Settlement("버거킹", 54000, "2024.07.20"),
            com.example.seureureuk.data.model.Settlement("샐러디", 34000, "2024.07.30"),
            com.example.seureureuk.data.model.Settlement("써브웨이", 14000, "2024.06.30"),
            com.example.seureureuk.data.model.Settlement("마라탕", 34000, "2024.05.30")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.settlement_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SettlementAdapter(settlements)
        recyclerView.adapter = adapter

        val emptyStateText = findViewById<TextView>(R.id.empty_state_text)
        if (settlements.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyStateText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateText.visibility = View.GONE
        }

        val addSettlementButton = findViewById<Button>(R.id.add_settlement_button)
        addSettlementButton.setOnClickListener {
            val intent = Intent(this, AddSettlementActivity::class.java)
            startActivity(intent)
            finish()
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
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

        val memberAddButton = findViewById<ImageView>(R.id.member_add_button)
        memberAddButton.setOnClickListener {
            showInviteOptionsDialog()
        }
    }

    private fun showInviteOptionsDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_invite_options, null)

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog) // 스타일 적용
            .setView(dialogView)
            .create()

        val shareLinkButton = dialogView.findViewById<LinearLayout>(R.id.share_invite_link_button)
        val inviteUserButton = dialogView.findViewById<LinearLayout>(R.id.invite_user_button)
        val cancelButton = dialogView.findViewById<ImageView>(R.id.cancel_button)

        shareLinkButton.setOnClickListener {
            val inviteLink = "https://yourapp.com/invite?code=ABCDE" // 예시 코드
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("invite link", inviteLink)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        inviteUserButton.setOnClickListener {
            dialog.dismiss()
            showInviteCodeDialog()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showInviteCodeDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_invite_code, null)

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val cancelButton = dialogView.findViewById<ImageView>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



}
