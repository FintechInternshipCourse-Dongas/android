package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MyPagePaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_payment)

        val accountContainer: LinearLayout = findViewById(R.id.account_container)
        accountContainer.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        val removeAccountButton: ImageView = findViewById(R.id.remove_account_button)
        removeAccountButton.setOnClickListener {
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
            val intent = Intent(this, DeletePaymentActivity::class.java)
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