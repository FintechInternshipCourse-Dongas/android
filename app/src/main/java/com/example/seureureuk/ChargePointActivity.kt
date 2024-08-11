package com.example.seureureuk

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.PointConversionRequest
import com.example.seureureuk.data.model.ResultResponseListAccountResponse
import com.example.seureureuk.data.model.ResultResponsePointConversionResponse
import com.example.seureureuk.data.model.ResultResponsePointInfoResponse
import com.example.seureureuk.network.RetrofitInstance
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

class ChargePointActivity : AppCompatActivity() {
    private lateinit var editRechargeAmount: EditText
    private lateinit var errorMessage: TextView
    private lateinit var rechargeButton: Button
    private lateinit var balanceTextView: TextView
    private lateinit var transferBankTextView: TextView

    companion object {
        const val MAX_AMOUNT = 2000000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge_point)

        editRechargeAmount = findViewById(R.id.edit_recharge_amount)
        errorMessage = findViewById(R.id.error_message)
        rechargeButton = findViewById(R.id.recharge_button)
        balanceTextView = findViewById(R.id.balance_after_transaction)
        transferBankTextView = findViewById(R.id.transfer_bank)

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
                        } else {
                            errorMessage.visibility = View.GONE
                        }

                        current = NumberFormat.getNumberInstance(Locale.US).format(parsed)
                        editRechargeAmount.setText(current)
                        editRechargeAmount.setSelection(current.length)

                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }

                    editRechargeAmount.addTextChangedListener(this)
                }
            }
        })

        rechargeButton.setOnClickListener {
            val amountText = editRechargeAmount.text.toString().replace(",", "")
            try {
                val amount = NumberFormat.getNumberInstance(Locale.US).parse(amountText)?.toLong() ?: 0L
                if (amount > MAX_AMOUNT) {
                    Toast.makeText(this, "Amount exceeds the maximum limit!", Toast.LENGTH_SHORT).show()
                } else {
                    convertPoints(amount.toInt())
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
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
            Log.d("ChargePointActivity", "Keypad height: $keypadHeight")
        }

        loadUserPoints()
        loadAccountData()
    }

    private fun convertPoints(amount: Int) {
        Log.d("ChargePointActivity", "Starting point conversion with amount: $amount")
        val apiService = RetrofitInstance.api
        val request = PointConversionRequest(amount)

        apiService.convertPoints(request).enqueue(object : Callback<ResultResponsePointConversionResponse> {
            override fun onResponse(call: Call<ResultResponsePointConversionResponse>, response: Response<ResultResponsePointConversionResponse>) {
                if (response.isSuccessful) {
                    val conversionResponse = response.body()?.data
                    if (conversionResponse != null) {
                        Log.d("ChargePointActivity", "Point conversion successful: $conversionResponse")

                        // 원래 입력된 충전 금액을 가져옴
                        val originalAmountText = editRechargeAmount.text.toString().replace(",", "")
                        val formattedPoint = NumberFormat.getNumberInstance(Locale.KOREA).format(originalAmountText.toLong()) + "원"

                        // Toast 메시지 대신 바로 다음 화면으로 이동
                        updateBalanceTextView(conversionResponse.balance)
                        navigateToFinishRechargePointActivity(formattedPoint)
                    } else {
                        Toast.makeText(this@ChargePointActivity, "응답 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "알 수 없는 오류"
                    Log.e("ChargePointActivity", "Failed to convert points. Error: $errorMessage")
                    Toast.makeText(this@ChargePointActivity, "포인트 충전에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<ResultResponsePointConversionResponse>, t: Throwable) {
                Log.e("ChargePointActivity", "Network failure: ${t.message}")
                Toast.makeText(this@ChargePointActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateBalanceTextView(balance: Int) {
        val formattedBalance = NumberFormat.getNumberInstance(Locale.KOREA).format(balance) + "원"
        balanceTextView.text = formattedBalance
        Log.d("ChargePointActivity", "Balance updated to: $formattedBalance")
    }

    private fun loadUserPoints() {
        val apiService = RetrofitInstance.api
        apiService.getPointInfo().enqueue(object : Callback<ResultResponsePointInfoResponse> {
            override fun onResponse(call: Call<ResultResponsePointInfoResponse>, response: Response<ResultResponsePointInfoResponse>) {
                if (response.isSuccessful) {
                    val pointInfo = response.body()?.data
                    if (pointInfo != null) {
                        val pointValue = pointInfo.point
                        val formattedPoint = NumberFormat.getNumberInstance(Locale.KOREA).format(pointValue) + "원"
                        balanceTextView.text = formattedPoint
                        Log.d("ChargePointActivity", "Point info loaded: $pointInfo")
                    } else {
                        Toast.makeText(this@ChargePointActivity, "포인트 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("ChargePointActivity", "Failed to load points info: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResultResponsePointInfoResponse>, t: Throwable) {
                Log.e("ChargePointActivity", "Network error: ${t.message}")
            }
        })
    }

    private fun navigateToFinishRechargePointActivity(formattedPoint: String) {
        val intent = Intent(this, FinishChargeActivity::class.java)
        intent.putExtra("amount_text", formattedPoint)
        Log.d("ChargePointActivity", "충전 금액: $formattedPoint")
        startActivity(intent)
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