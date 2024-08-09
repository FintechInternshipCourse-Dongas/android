package com.example.seureureuk

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.seureureuk.ui.viewmodel.GroupViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createGroupButton = findViewById<ImageView>(R.id.button_add)
        createGroupButton.setOnClickListener {
            showCreateGroupDialog()
        }

        val createGroupView = findViewById<CardView>(R.id.add_group_button)
        createGroupView.setOnClickListener {
            showCreateGroupDialog()
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation_bar)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.navigation_my -> {
                    startActivity(Intent(this, MyPageActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun showCreateGroupDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_group, null)

        val dialogBuilder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .setCancelable(true)

        val dialog = dialogBuilder.create()
        dialog.show()

        val cancelButton = dialogView.findViewById<ImageView>(R.id.cancel_button)
        val createGroupButton = dialogView.findViewById<Button>(R.id.group_creation_button)
        val joinWithInviteCodeButton = dialogView.findViewById<Button>(R.id.join_with_invite_code_button)
        val groupNameEditText = dialogView.findViewById<EditText>(R.id.group_name_edit_text)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        createGroupButton.setOnClickListener {
            val groupName = groupNameEditText.text.toString()

            if (groupName.isNotEmpty()) {
                groupViewModel.createGroup(groupName)

                groupViewModel.createGroupResponse.observe(this) { success ->
                    if (success) {
                        Log.d("MainActivity", "Group created successfully!")
                        dialog.dismiss()

                        val viewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
                        val groupId = 1 // 나중에 수정
                        viewModel.fetchGroupSettlement(groupId)
                        viewModel.groupSettlement.observe(this) { groupSettlementResponse ->
                            if (groupSettlementResponse != null) {
                                val intent = Intent(this, SettlementListActivity::class.java)
                                intent.putExtra("group_settlement", groupSettlementResponse)
                                intent.putExtra("groupId", groupId)
                                startActivity(intent)
                            } else {
                                Log.d("MainActivity", "정산 내역을 불러오는 데 실패했습니다.")
                            }
                        }
                    } else {
                        Log.d("MainActivity", "Failed to create group")
                    }
                }

                groupViewModel.error.observe(this) { errorMessage ->
                    Log.e("MainActivity", errorMessage)
                }

            } else {
                Log.d("MainActivity", "Please enter a group name")
            }
        }

        joinWithInviteCodeButton.setOnClickListener {
            dialog.dismiss()
            showJoinWithInviteCodeDialog()
        }
    }

    private fun showJoinWithInviteCodeDialog() {
        val joinDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input_invite_code, null)

        val joinDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(joinDialogView)
            .setCancelable(true)

        val joinDialog = joinDialogBuilder.create()
        joinDialog.show()

        val cancelButton = joinDialogView.findViewById<ImageView>(R.id.cancel_button)
        val groupJoinButton = joinDialogView.findViewById<Button>(R.id.group_join_button)
        val inviteCodeEditText = joinDialogView.findViewById<EditText>(R.id.invite_code_edit_text)

        cancelButton.setOnClickListener {
            joinDialog.dismiss()
        }

        groupJoinButton.setOnClickListener {
            val inviteCode = inviteCodeEditText.text.toString()
            joinDialog.dismiss()
            val intent = Intent(this, SettlementListActivity::class.java)
            startActivity(intent)
        }
    }
}