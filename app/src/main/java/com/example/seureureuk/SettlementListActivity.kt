// SettlementListActivity.kt
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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupMember
import com.example.seureureuk.data.model.GroupSettlementResponse
import com.example.seureureuk.ui.viewmodel.GroupViewModel
import com.example.seureureuk.data.model.Settlement
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettlementListActivity : AppCompatActivity() {

    private val groupViewModel: GroupViewModel by viewModels()
    private lateinit var adapter: GroupSettlementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_list)

        val recyclerView = findViewById<RecyclerView>(R.id.settlement_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GroupSettlementAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        val groupSettlement: GroupSettlementResponse? = intent.getParcelableExtra("group_settlement")
        if (groupSettlement != null) {
            adapter.updateData(listOf(groupSettlement))
            addMembersToLayout(groupSettlement.groupMembers)
        } else {
            Toast.makeText(this, "SettlementListActivity - 정산 내역을 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val addSettlementButton = findViewById<Button>(R.id.add_settlement_button)
        addSettlementButton.setOnClickListener {
            val intent = Intent(this, AddSettlementActivity::class.java)
            startActivity(intent)
        }

        val memberAddButton = findViewById<ImageView>(R.id.member_add_button)
        memberAddButton.setOnClickListener {
            showInviteOptionsDialog()
        }

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home navigation
                    true
                }
                R.id.navigation_my -> {
                    // Handle my navigation
                    true
                }
                else -> false
            }
        }
    }

    private fun addMembersToLayout(members: List<GroupMember>) {
        val membersLayout = findViewById<LinearLayout>(R.id.members_layout_inner)

        for (member in members) {
            val memberView = LayoutInflater.from(this).inflate(R.layout.group_settlement_member_item, membersLayout, false)

            val memberNameTextView = memberView.findViewById<TextView>(R.id.member_name)
            memberNameTextView.text = member.memberName

            val memberImageView = memberView.findViewById<ImageView>(R.id.member_image)
            val resourceId = resources.getIdentifier("ic_member_${member.id}", "drawable", packageName)
            memberImageView.setImageResource(resourceId)

            membersLayout.addView(memberView)
        }
    }

    private fun showInviteOptionsDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_invite_options, null)

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val shareLinkButton = dialogView.findViewById<LinearLayout>(R.id.share_invite_link_button)
        val inviteUserButton = dialogView.findViewById<LinearLayout>(R.id.invite_user_button)
        val cancelButton = dialogView.findViewById<ImageView>(R.id.cancel_button)

        shareLinkButton.setOnClickListener {
            val inviteLink = "https://yourapp.com/invite?code=ABCDE"
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

    private fun updateUI(groupMembers: List<GroupMember>) {
        // Update UI with group members
        // You can use RecyclerView or any other view to display group members
    }
}
