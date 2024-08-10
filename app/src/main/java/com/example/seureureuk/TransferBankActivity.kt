package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.PointConversionRequest
import com.example.seureureuk.data.model.ResultResponsePointConversionResponse
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class TransferBankActivity : AppCompatActivity() {

    private lateinit var editTransferAmount: EditText
    private var currentBalance: Int = 0
    private var currentPoints: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_bank)

        editTransferAmount = findViewById(R.id.edit_transfer_amount)

        val confirmBtn = findViewById<Button>(R.id.confirm_button)
        val backBtn = findViewById<ImageView>(R.id.back_button)

        Log.d("TransferBankActivity", "Activity created, setting up listeners")

        confirmBtn.setOnClickListener {
            val amountText = editTransferAmount.text.toString().replace("[,원]".toRegex(), "").trim() // Trim the input string
            if (amountText.isNotEmpty()) {
                try {
                    val amount = amountText.toLong()
                    Log.d("TransferBankActivity", "Amount entered: $amount")
                    convertPoints2(amount.toInt())
                } catch (e: NumberFormatException) {
                    Log.e("TransferBankActivity", "Invalid amount entered: $amountText", e)
                    showToast("유효한 금액을 입력하세요.")
                }
            } else {
                Log.w("TransferBankActivity", "Amount input is empty")
                showToast("금액을 입력하세요.")
            }
        }

        backBtn.setOnClickListener {
            Log.d("TransferBankActivity", "Back button clicked, navigating to ShowAccountActivity")
            val intent = Intent(this, ShowAccountActivity::class.java)
            startActivity(intent)
        }

        editTransferAmount.addTextChangedListener(object : TextWatcher {
            private var currentText = ""
            private val decimalFormat = DecimalFormat("#,###", DecimalFormatSymbols(Locale.getDefault()))

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed while text is changing
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != currentText) {
                    editTransferAmount.removeTextChangedListener(this)

                    // Remove commas and "원" for processing
                    val cleanString = s.toString().replace("[,원]".toRegex(), "").trim() // Trim the input string

                    if (cleanString.isNotEmpty()) {
                        try {
                            val parsedValue = cleanString.toLong()
                            val formatted = decimalFormat.format(parsedValue)
                            currentText = "$formatted 원"
                            Log.d("TransferBankActivity", "Formatted text: $currentText")
                            editTransferAmount.setText(currentText)
                            editTransferAmount.setSelection(currentText.length - 2) // Position cursor before " 원"

                            // Update the style of the EditText to be bold and larger
                            editTransferAmount.textSize = 35f
                            editTransferAmount.setTextColor(resources.getColor(android.R.color.black))
                            editTransferAmount.setTypeface(editTransferAmount.typeface, android.graphics.Typeface.BOLD)
                        } catch (e: NumberFormatException) {
                            Log.e("TransferBankActivity", "Error parsing amount: $cleanString", e)
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

    private fun convertPoints2(amount: Int) {
        Log.d("TransferBankActivity", "Starting point deduction for amount: $amount")

        // Create the request with the original amount (positive value)
        val apiService = RetrofitInstance.api
        val request = PointConversionRequest(amount)

        apiService.convertPoints(request).enqueue(object : Callback<ResultResponsePointConversionResponse> {
            override fun onResponse(call: Call<ResultResponsePointConversionResponse>, response: Response<ResultResponsePointConversionResponse>) {
                if (response.isSuccessful) {
                    // Assume the API returns the updated balance and points after deduction
                    val conversionResponse = response.body()?.data
                    if (conversionResponse != null) {
                        Log.d("TransferBankActivity", "Point deduction successful: $conversionResponse")

                        // Deduct the specified amount from the points returned by the API
                        val updatedPoints = conversionResponse.point - amount
                        if (updatedPoints < 0) {
                            Log.e("TransferBankActivity", "Insufficient points for deduction.")
                            showToast("포인트가 부족합니다.")
                            return
                        }

                        // Update the current balance and points with the new values from the response
                        currentBalance = conversionResponse.balance
                        currentPoints = conversionResponse.point

                        showToast("포인트가 성공적으로 차감되었습니다.")

                        // Create intent and start FinishTransferActivity
                        val intent = Intent(this@TransferBankActivity, FinishTransferActivity::class.java)
                        intent.putExtra("amount", amount.toString())  // Pass amount as a String
                        startActivity(intent)
                        finish() // Optionally finish the current activity if no longer needed
                    } else {
                        Log.e("TransferBankActivity", "Response body is null or data is missing.")
                        showToast("오류가 발생했습니다. 다시 시도해주세요.")
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("TransferBankActivity", "Failed to deduct points. Error: $errorMessage")
                    errorMessage?.let { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResultResponsePointConversionResponse>, t: Throwable) {
                Log.e("TransferBankActivity", "Network failure during point deduction", t)
                showToast(t.message ?: "Unknown error")
            }
        })
    }

    private fun showToast(message: String) {
        Log.d("TransferBankActivity", "Showing toast: $message")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}