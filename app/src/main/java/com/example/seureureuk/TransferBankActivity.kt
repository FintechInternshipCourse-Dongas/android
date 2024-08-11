package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class TransferBankActivity : AppCompatActivity() {

    private lateinit var editTransferAmount: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_bank)

        val confirmBtn = findViewById<Button>(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val intent = Intent(this, FinishTransferActivity::class.java)
            startActivity(intent)
        }

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, ShowAccountActivity::class.java)
            startActivity(intent)
        }

        editTransferAmount = findViewById(R.id.edit_transfer_amount)

        editTransferAmount.addTextChangedListener(object : TextWatcher {
            private var currentText = ""
            private val decimalFormat = DecimalFormat("#,###", DecimalFormatSymbols(Locale.getDefault()))

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != currentText) {
                    editTransferAmount.removeTextChangedListener(this)

                    // Remove commas and "원" for processing
                    val cleanString = s.toString().replace("[,원]".toRegex(), "")

                    if (cleanString.isNotEmpty()) {
                        try {
                            val parsedValue = cleanString.toLong()
                            val formatted = decimalFormat.format(parsedValue)
                            currentText = "$formatted 원"
                            editTransferAmount.setText(currentText)
                            editTransferAmount.setSelection(currentText.length - 2) // Position cursor before " 원"

                            // Update the style of the EditText to be bold and larger
                            editTransferAmount.textSize = 35f
                            editTransferAmount.setTextColor(resources.getColor(android.R.color.black))
                            editTransferAmount.setTypeface(editTransferAmount.typeface, android.graphics.Typeface.BOLD)
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }
                    } else {
                        currentText = ""
                        editTransferAmount.textSize = 20f // Reset to default size when input is empty
                        editTransferAmount.setTextColor(resources.getColor(android.R.color.darker_gray))
                        editTransferAmount.setTypeface(editTransferAmount.typeface, android.graphics.Typeface.NORMAL)
                    }

                    editTransferAmount.addTextChangedListener(this)
                }
            }
        })
    }
}