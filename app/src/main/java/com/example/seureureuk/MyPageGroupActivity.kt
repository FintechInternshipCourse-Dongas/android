package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MyPageGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_group)

        val removeGroup1 = findViewById<CardView>(R.id.group_1)
        removeGroup1.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGroupBtn1: ImageView = findViewById(R.id.remove_group_button_1)
        removeGroupBtn1.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        val removeGroup2 = findViewById<CardView>(R.id.group_2)
        removeGroup2.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGroupBtn2: ImageView = findViewById(R.id.remove_group_button_2)
        removeGroupBtn2.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        val removeGroup3 = findViewById<CardView>(R.id.group_3)
        removeGroup3.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGroupBtn3: ImageView = findViewById(R.id.remove_group_button_3)
        removeGroupBtn3.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        val removeGroup4 = findViewById<CardView>(R.id.group_4)
        removeGroup4.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGroupBtn4: ImageView = findViewById(R.id.remove_group_button_4)
        removeGroupBtn4.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        val createGroupView = findViewById<CardView>(R.id.add_group_button)
        createGroupView.setOnClickListener {
            showCreateGroupDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("정말 삭제하시겠습니까?")

        builder.setPositiveButton("네") { dialog, _ ->
            val intent = Intent(this, DeleteGroupActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        builder.setNegativeButton("아니요") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.apply {
            setTextColor(resources.getColor(R.color.gray, theme))
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
            setTextColor(resources.getColor(R.color.blue, theme))
        }
    }

    private fun showCreateGroupDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_group, null)

        val dialogBuilder = android.app.AlertDialog.Builder(this, R.style.CustomAlertDialog)
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

        val joinDialogBuilder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
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