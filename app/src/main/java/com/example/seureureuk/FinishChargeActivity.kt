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
import java.text.NumberFormat
import java.util.Locale

class FinishChargeActivity : AppCompatActivity() {

    private lateinit var balanceTextView: TextView
    private lateinit var transferBankTextView: TextView
    private lateinit var pointTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.seureureuk.R.layout.activity_charge_finish)

        pointTextView = findViewById(R.id.amount_text)
        balanceTextView = findViewById(R.id.point_amount)
        transferBankTextView = findViewById(R.id.transfer_bank)

        val confirmBtn = findViewById<Button>(com.example.seureureuk.R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val intent = Intent(this, com.example.seureureuk.MyPageActivity::class.java)
            startActivity(intent)
        }

        // 이전 액티비티에서 전달된 금액 값을 받아서 TextView에 설정
        val amountText = intent.getStringExtra("amount_text")
        if (amountText != null) {
            pointTextView.text = "$amountText"
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
                        balanceTextView.text = "$formattedPoint 원"
                    } else {
                        Toast.makeText(
                            this@FinishChargeActivity,
                            "포인트 정보를 불러올 수 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("FinishChargeActivity", "Failed to load points info: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResultResponsePointInfoResponse>, t: Throwable) {
                Log.e("FinishChargeActivity", "Network error: ${t.message}")
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
                        "FinishChargeActivity",
                        "Account data loaded successfully: ${accountList.size} accounts found"
                    )

                    // mainAccount가 true인 계좌 찾기
                    val mainAccount = accountList.find { it.mainAccount }
                    if (mainAccount != null) {
                        val bankNameAndNumber = "${mainAccount.bankName} ${mainAccount.accountNumber}"
                        transferBankTextView.text = bankNameAndNumber
                        Log.d("FinishChargeActivity", "Main account found: $bankNameAndNumber")
                    } else {
                        Log.d("FinishChargeActivity", "No main account found.")
                        transferBankTextView.text = "주 계좌가 없습니다."
                    }

                    // 계좌 정보 로그 출력 (JSON 형식으로 변환하여 로그로 출력)
                    val gson = Gson()
                    val accountDataJson = gson.toJson(accountList)
                    Log.d("FinishChargeActivity", "Account Data: $accountDataJson")

                } else {
                    // 에러 처리
                    val errorMessage = response.errorBody()?.string()
                    Log.e("FinishChargeActivity", "Failed to load accounts. Error: $errorMessage")
                    errorMessage?.let { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResultResponseListAccountResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("FinishChargeActivity", "Network failure: ${t.message}")
                showToast(t.message ?: "Unknown error")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}