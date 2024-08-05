package com.example.seureureuk

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

class ChargePointActivity : AppCompatActivity() {
    private lateinit var editRechargeAmount: EditText
    private lateinit var errorMessage: TextView
    private lateinit var rechargeButton: Button

    companion object {
        const val MAX_AMOUNT = 2000000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_point)

        editRechargeAmount = findViewById(R.id.edit_recharge_amount)
        errorMessage = findViewById(R.id.error_message)
        rechargeButton = findViewById(R.id.recharge_button)

        editRechargeAmount.addTextChangedListener(object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    editRechargeAmount.removeTextChangedListener(this)

                    val cleanString = s.toString().replace(",", "")

                    try {
                        val parsed = cleanString.toDouble()
                        if (parsed > MAX_AMOUNT) {
                            errorMessage.visibility = View.VISIBLE
                        }
                        else {
                            errorMessage.visibility = View.GONE
                        }

                        current = NumberFormat.getNumberInstance(Locale.US).format(parsed)
                        editRechargeAmount.setText(current)
                        editRechargeAmount.setSelection(current.length)

                    }
                    catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }

                    editRechargeAmount.addTextChangedListener(this)
                }
            }
        })

        rechargeButton.setOnClickListener {
            val amountText = editRechargeAmount.text.toString().replace(",", "")
            try {
                val amount = NumberFormat.getNumberInstance(Locale.US).parse(amountText)?.toDouble() ?: 0.0
                if (amount > MAX_AMOUNT) {
                    Toast.makeText(this, "Amount exceeds the maximum limit!", Toast.LENGTH_SHORT).show()
                }
                else {
                }
            }
            catch (e: ParseException) {
                e.printStackTrace()
            }

            val intent = Intent(this, FinishChargeActivity::class.java)
            startActivity(intent)
        }

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        val rootView = findViewById<View>(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
        }
    }
}