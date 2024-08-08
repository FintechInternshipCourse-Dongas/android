package com.example.seureureuk

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupInfoResponse
import com.example.seureureuk.ui.viewmodel.GroupViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private val groupViewModel: GroupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val createGroupButton = findViewById<ImageView>(R.id.button_add)
        createGroupButton.setOnClickListener {
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

        val recyclerView: RecyclerView = findViewById(R.id.group_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GroupAdapter(emptyList(), this)
        recyclerView.adapter = adapter

        groupViewModel.groups.observe(this) { groups ->
            groups?.let {
                Log.d("HomeActivity", "Updating adapter with groups: $it")
                adapter.updateData(it)
            }
        }

        groupViewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        val groups = intent.getParcelableArrayListExtra<GroupInfoResponse>("groups")
        if (groups != null && groups.isNotEmpty()) {
            groupViewModel.setAllGroups(groups)
        } else {
            groupViewModel.fetchAllGroups()
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
            dialog.dismiss()
            val intent = Intent(this, SettlementListActivity::class.java)
            startActivity(intent)
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