package com.example.seureureuk

import GroupSettlementAdapter
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.seureureuk.data.model.GroupInviteResponseData
import com.example.seureureuk.data.model.GroupMemberResponse
import com.example.seureureuk.data.model.GroupSettlementResponse
import com.example.seureureuk.network.RetrofitInstance
import com.example.seureureuk.ui.viewmodel.GroupViewModel
import com.example.seureureuk.ui.viewmodel.SettlementViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettlementListActivity : AppCompatActivity() {
    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settlement_list)

        val recyclerView = findViewById<RecyclerView>(R.id.settlement_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GroupSettlementAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        val groupMembers = intent.getParcelableArrayListExtra<GroupMemberResponse>("groupMembers") ?: arrayListOf()
        val groupSettlements = intent.getParcelableArrayListExtra<GroupSettlementResponse>("groupSettlements") ?: arrayListOf()
        val groupId = intent.getIntExtra("groupId", -1)
        val groupName = intent.getStringExtra("groupName")
        if (groupSettlements != null) {
            adapter.updateData(groupSettlements)
            addMembersToLayout(groupMembers)
        } else {
            Log.d("SettlementListActivity", "정산 내역이 존재하지 않습니다.")
            val emptyStateTextView = findViewById<TextView>(R.id.empty_state_text)
            emptyStateTextView.visibility = View.VISIBLE
        }

        val pageTitleTextView = findViewById<TextView>(R.id.page_title)
        pageTitleTextView.text = groupName
        if (groupName != null) {
            Log.d("SettlementListActivity", groupName)
        }


        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            groupViewModel.fetchAllGroups()
            finish()
        }

        val addSettlementButton = findViewById<Button>(R.id.add_settlement_button)
        addSettlementButton.setOnClickListener {
            val intent = Intent(this, AddSettlementActivity::class.java)
            intent.putExtra("groupMembers", groupMembers)
            intent.putExtra("groupId", groupId)
            startActivity(intent)
        }

        val memberAddButton = findViewById<ImageView>(R.id.member_add_button)
        memberAddButton.setOnClickListener {
            showInviteOptionsDialog(groupId)
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

    private fun addMembersToLayout(members: List<GroupMemberResponse>) {
        val membersLayout = findViewById<LinearLayout>(R.id.members_layout_inner)

        for (member in members) {
            val memberView = LayoutInflater.from(this).inflate(R.layout.group_settlement_member_item, membersLayout, false)

            val memberNameTextView = memberView.findViewById<TextView>(R.id.member_name)
            memberNameTextView.text = member.memberName

            val memberImageView = memberView.findViewById<ImageView>(R.id.member_image)
            val icNum = (member.id % 6) + 1
            val resourceId = resources.getIdentifier("ic_member_${icNum}", "drawable", packageName)
            memberImageView.setImageResource(resourceId)

            membersLayout.addView(memberView)
        }
    }

    private fun showInviteOptionsDialog(groupId: Int) {
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

            val apiService = RetrofitInstance.api
            apiService.createInviteCode(groupId).enqueue(object : Callback<GroupInviteResponseData> {
                override fun onResponse(call: Call<GroupInviteResponseData>, response: Response<GroupInviteResponseData>) {
                    if (response.isSuccessful) {
                        val inviteResponse = response.body()
                        inviteResponse?.let {
                            val inviteCode = it.data.invitationCode
                            showInviteCodeDialog(inviteCode)
                        }
                    } else {
                        Log.d("SettlementListActivity","createInviteCode failed with status: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<GroupInviteResponseData>, t: Throwable) {
                    Log.d("SettlementListActivity", "Network error: ${t.message}")
                }
            })
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showInviteCodeDialog(inviteCode: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_invite_code, null)

        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val cancelButton = dialogView.findViewById<ImageView>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        Log.d("SettlementListaActivity", "Created Invite Code: ${inviteCode}")
        val inviteCodeTextView = dialogView.findViewById<TextView>(R.id.invite_code_text_view)
        inviteCodeTextView.text = inviteCode

        dialog.show()
    }


}
