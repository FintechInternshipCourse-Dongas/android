package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MyPageGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_group)

        val removeGrouptBtn1: ImageView = findViewById(R.id.remove_group_button_1)
        removeGrouptBtn1.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGrouptBtn2: ImageView = findViewById(R.id.remove_group_button_2)
        removeGrouptBtn2.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGrouptBtn3: ImageView = findViewById(R.id.remove_group_button_3)
        removeGrouptBtn3.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeGrouptBtn4: ImageView = findViewById(R.id.remove_group_button_4)
        removeGrouptBtn4.setOnClickListener {
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