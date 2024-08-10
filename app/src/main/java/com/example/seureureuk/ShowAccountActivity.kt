package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.AccountResponse
import com.example.seureureuk.data.model.ResultResponseListAccountResponse
import com.example.seureureuk.network.RetrofitInstance
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowAccountActivity : AppCompatActivity() {

    private lateinit var accountSection: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)

        accountSection = findViewById(R.id.account_section)

        loadAccountData()

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadAccountData() {
        val apiService = RetrofitInstance.api
        apiService.getAccounts().enqueue(object : Callback<ResultResponseListAccountResponse> {
            override fun onResponse(call: Call<ResultResponseListAccountResponse>, response: Response<ResultResponseListAccountResponse>) {
                if (response.isSuccessful) {
                    val accountList = response.body()?.data ?: emptyList()
                    Log.d("ShowAccountActivity", "Account data loaded successfully: ${accountList.size} accounts found")

                    // 계좌 정보 로그 출력 (JSON 형식으로 변환하여 로그로 출력)
                    val gson = Gson()
                    val accountDataJson = gson.toJson(accountList)
                    Log.d("ShowAccountActivity", "Account Data: $accountDataJson")

                    // 계좌 이름과 번호를 추출하여 populateAccountViews 함수 호출
                    populateAccountViews(accountList)
                } else {
                    // 에러 처리
                    val errorMessage = response.errorBody()?.string()
                    Log.e("ShowAccountActivity", "Failed to load accounts. Error: $errorMessage")
                    errorMessage?.let { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResultResponseListAccountResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("ShowAccountActivity", "Network failure: ${t.message}")
                showToast(t.message ?: "Unknown error")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun populateAccountViews(accountList: List<AccountResponse>) {
        val inflater = LayoutInflater.from(this)
        val parentLayout = findViewById<LinearLayout>(R.id.account_section)

        for ((index, account) in accountList.withIndex()) {
            val accountView = inflater.inflate(R.layout.item_account_transfer, parentLayout, false)

            val bankNameTextView = accountView.findViewById<TextView>(R.id.bank_name)
            val accountNumberTextView = accountView.findViewById<TextView>(R.id.account_number)
            val bankImageView = accountView.findViewById<ImageView>(R.id.bank_image)
            val primaryAccountButton = accountView.findViewById<Button>(R.id.primary_account_button)

            bankNameTextView.text = account.bankName
            accountNumberTextView.text = account.accountNumber

            // 은행에 따라 이미지 설정
            bankImageView.setImageResource(getBankImageResource(account.bankName))

            // 첫 번째 계좌는 주계좌로 지정
            if (account.mainAccount) {
                primaryAccountButton.visibility = View.VISIBLE
            } else {
                primaryAccountButton.visibility = View.GONE
            }

            // 계좌 클릭 시 TransferBankActivity로 이동
            accountView.setOnClickListener {
                val intent = Intent(this, TransferBankActivity::class.java)
                intent.putExtra("bank_name", account.bankName)
                intent.putExtra("account_number", account.accountNumber)
                startActivity(intent)
            }

            // 동적으로 "내 계좌" 아래에 뷰를 추가
            parentLayout.addView(accountView)
        }
    }

    private fun getBankImageResource(bankName: String): Int {
        return when (bankName) {
            "NH농협" -> R.drawable.ic_nh_bank
            "카카오뱅크" -> R.drawable.ic_kakao_bank
            "KB국민" -> R.drawable.ic_kb_bank
            "토스뱅크" -> R.drawable.ic_toss_bank
            "신한" -> R.drawable.ic_shinhan_bank
            "우리" -> R.drawable.ic_woori_bank
            else -> R.drawable.ic_kb_bank
        }
    }
}