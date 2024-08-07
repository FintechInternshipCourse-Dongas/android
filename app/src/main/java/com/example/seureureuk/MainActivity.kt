package com.example.seureureuk

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val logo = findViewById<ImageView>(R.id.logo)
//        logo.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        val CreateGroupButton = findViewById<ImageView>(R.id.button_add)
        CreateGroupButton.setOnClickListener {
            showCreateGroupDialog()
        }

        val createGroupView = findViewById<CardView>(R.id.add_group_button)
        createGroupView.setOnClickListener {
            showCreateGroupDialog()
        }

//        val navigationHome = findViewById<LinearLayout>(R.id.navigation_home)
//        navigationHome.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        val navigationMy = findViewById<LinearLayout>(R.id.navigation_my)
        navigationMy.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
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
            // Handle join with invite code logic
            dialog.dismiss()
        }
    }
}