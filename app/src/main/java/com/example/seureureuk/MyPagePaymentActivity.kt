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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.AccountResponse
import com.example.seureureuk.data.model.ResultResponseListAccountResponse
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPagePaymentActivity : AppCompatActivity() {

    private lateinit var accountSection: LinearLayout
    private val accountViewMap = mutableMapOf<Long, View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_payment)

        accountSection = findViewById(R.id.account_section)

        loadAccountData()

        val add_account_Btn: LinearLayout = findViewById(R.id.add_account_button)
        add_account_Btn.setOnClickListener {
            val intent = Intent(this, PaymentMethodRegistrationActivity::class.java)
            startActivity(intent)
        }

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
                    Log.d("MyPagePaymentActivity", "Account data loaded successfully: ${accountList.size} accounts found")

                    // Clear previous views and map entries
                    accountSection.removeAllViews()
                    accountViewMap.clear()

                    // Populate the views with the account data
                    populateAccountViews(accountList)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("MyPagePaymentActivity", "Failed to load accounts. Error: $errorMessage")
                    errorMessage?.let { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResultResponseListAccountResponse>, t: Throwable) {
                Log.e("MyPagePaymentActivity", "Network failure: ${t.message}")
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
            val accountView = inflater.inflate(R.layout.item_account_mypage, parentLayout, false)

            val bankNameTextView = accountView.findViewById<TextView>(R.id.bank_name)
            val accountNumberTextView = accountView.findViewById<TextView>(R.id.account_number)
            val bankImageView = accountView.findViewById<ImageView>(R.id.bank_image)
            val primaryAccountButton = accountView.findViewById<Button>(R.id.primary_account_button)
            val removeAccountButton = accountView.findViewById<ImageView>(R.id.remove_account_button)

            bankNameTextView.text = account.bankName
            accountNumberTextView.text = account.accountNumber

            // 은행에 따라 이미지 설정
            bankImageView.setImageResource(getBankImageResource(account.bankName))

            // 첫 번째 계좌는 주계좌로 지정
            if (index == 0) {
                primaryAccountButton.visibility = View.VISIBLE
            } else {
                primaryAccountButton.visibility = View.GONE
            }

            // Store the view reference in the map
            accountViewMap[account.id.toLong()] = accountView

            // Append the view to the layout
            parentLayout.addView(accountView)

            removeAccountButton.setOnClickListener {
                showDeleteConfirmationDialog(account.id.toLong())
            }
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

    private fun showDeleteConfirmationDialog(accountId: Long) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("정말 삭제하시겠습니까?")

        builder.setPositiveButton("네") { dialog, _ ->
            deleteAccount(accountId)
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

    private fun deleteAccount(accountId: Long) {
        val apiService = RetrofitInstance.api
        apiService.deleteAccount(accountId).enqueue(object : Callback<ResultResponseVoid> {
            override fun onResponse(call: Call<ResultResponseVoid>, response: Response<ResultResponseVoid>) {
                if (response.isSuccessful) {
                    showToast("계좌가 삭제되었습니다.")

                    // Remove the view associated with the deleted account
                    accountViewMap[accountId]?.let { accountView ->
                        accountSection.removeView(accountView)
                        accountViewMap.remove(accountId)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("MyPagePaymentActivity", "Failed to delete account. Error: $errorMessage")
                    errorMessage?.let { showToast(it) }
                }
            }

            override fun onFailure(call: Call<ResultResponseVoid>, t: Throwable) {
                Log.e("MyPagePaymentActivity", "Network failure: ${t.message}")
                showToast(t.message ?: "Unknown error")
            }
        })
    }
}