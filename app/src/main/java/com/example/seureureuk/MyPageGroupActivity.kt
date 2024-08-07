package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
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
}