package com.example.seureureuk

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {

    private lateinit var selectedTelecomText: TextView
//    private lateinit var selectTelecomButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.seureureuk.R.layout.activity_sign_up)

        val backBtn = findViewById<ImageView>(com.example.seureureuk.R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val confirmBtn = findViewById<Button>(com.example.seureureuk.R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val intent = Intent(this, CertificateUserActivity::class.java)
            startActivity(intent)
        }

        selectedTelecomText = findViewById<TextView>(com.example.seureureuk.R.id.selectedTelecomText)
//        selectTelecomButton = findViewById<Button>(com.example.seureureuk.R.id.selectTelecomButton)
        selectedTelecomText.setOnClickListener {
            showTelecomSelectionDialog()
        }
    }

    private fun showTelecomSelectionDialog() {
        val telecomOptions = arrayOf("SKT", "KT", "LG U+", "SKT 알뜰폰", "KT 알뜰폰", "LG U+ 알뜰폰")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("통신사를 선택해주세요")
        builder.setItems(telecomOptions) { _, which ->
            val selectedTelecom = telecomOptions[which]
            selectedTelecomText.text = "$selectedTelecom"
        }
        builder.setNegativeButton("취소", null)
        builder.show()
    }
}