package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.ResultResponseListAccountResponse
import com.example.seureureuk.data.model.ResultResponsePointInfoResponse
import com.example.seureureuk.network.RetrofitInstance
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class FinishTransferActivity : AppCompatActivity() {

    private lateinit var amountTextTextView: TextView
    private lateinit var transferBankTextView: TextView
    private lateinit var pointAmountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_finish)

        amountTextTextView = findViewById(R.id.amount_text)
        transferBankTextView = findViewById(R.id.transfer_bank)
        pointAmountTextView = findViewById(R.id.point_amount)

        val confirmBtn = findViewById<Button>(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        // 이전 액티비티에서 전달된 금액 값을 받아서 TextView에 설정
        val amountText = intent.getStringExtra("amount")
        Log.d("FinishTransferActivity", "Failed to load points info: $amountText")
        if (amountText != null) {
            val formattedAmountText = formatAmount(amountText)
            amountTextTextView.text = "$formattedAmountText"
        }

        loadUserPoints()
        loadAccountData()
    }

    private fun loadUserPoints() {
        val apiService = RetrofitInstance.api
        apiService.getPointInfo().enqueue(object : Callback<ResultResponsePointInfoResponse> {
            override fun onResponse(
                call: Call<ResultResponsePointInfoResponse>,
                response: Response<ResultResponsePointInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val pointInfo = response.body()?.data
                    if (pointInfo != null) {
                        val pointValue = pointInfo.point
                        val formattedPoint = NumberFormat.getNumberInstance(Locale.KOREA).format(pointValue)
                        pointAmountTextView.text = "$formattedPoint 원"
                    } else {
                        Toast.makeText(
                            this@FinishTransferActivity,
                            "포인트 정보를 불러올 수 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("FinishTransferActivity", "Failed to load points info: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResultResponsePointInfoResponse>, t: Throwable) {
                Log.e("FinishTransferActivity", "Network error: ${t.message}")
            }
        })
    }

    private fun loadAccountData() {
        val apiService = RetrofitInstance.api
        apiService.getAccounts().enqueue(object : Callback<ResultResponseListAccountResponse> {
            override fun onResponse(
                call: Call<ResultResponseListAccountResponse>,
                response: Response<ResultResponseListAccountResponse>
            ) {
                if (response.isSuccessful) {
                    val accountList = response.body()?.data ?: emptyList()
                    Log.d(
                        "FinishTransferActivity",
                        "Account data loaded successfully: ${accountList.size} accounts found"
                    )

                    // mainAccount가 true인 계좌 찾기
                    val mainAccount = accountList.find { it.mainAccount }
                    if (mainAccount != null) {
                        val bankNameAndNumber = "${mainAccount.bankName} ${mainAccount.accountNumber}"
                        transferBankTextView.text = bankNameAndNumber
                        Log.d("FinishTransferActivity", "Main account found: $bankNameAndNumber")
                    } else {
                        Log.d("FinishTransferActivity", "No main account found.")
                        transferBankTextView.text = "주 계좌가 없습니다."
                    }

                    // 계좌 정보 로그 출력 (JSON 형식으로 변환하여 로그로 출력)
                    val gson = Gson()
                    val accountDataJson = gson.toJson(accountList)
                    Log.d("FinishTransferActivity", "Account Data: $accountDataJson")

                } else {
                    // 에러 처리
                    val errorMessage = response.errorBody()?.string()
                    Log.e("FinishTransferActivity", "Failed to load accounts. Error: $errorMessage")
                    errorMessage?.let { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResultResponseListAccountResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("FinishTransferActivity", "Network failure: ${t.message}")
                showToast(t.message ?: "Unknown error")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun formatAmount(amountText: String): String {
        // Convert the amountText to a Long
        val amount = amountText.toLong()

        // Create a DecimalFormat instance for formatting with commas
        val decimalFormat = DecimalFormat("#,###")

        // Format the amount and append "원"
        return "${decimalFormat.format(amount)}원"
    }
}