package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.AccountRegisterRequest
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountInfoInputActivity : AppCompatActivity(), BankSelectionBottomSheet.OnBankSelectedListener {

    private lateinit var bankSelector: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info_input)

        RetrofitInstance.initializeToken(this)

        bankSelector = findViewById<TextView>(R.id.bank_selector)
        val accountNumberInput = findViewById<EditText>(R.id.account_number_input)
        val accountVerificationButton = findViewById<Button>(R.id.account_verification_button)

        bankSelector.setOnClickListener {
            val bottomSheet = BankSelectionBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        accountVerificationButton.setOnClickListener {
            val bankName = bankSelector.text.toString()
            val accountNumber = accountNumberInput.text.toString()

            Log.d("AccountInfoInputActivity", "Collected user data: $bankName, $accountNumber")

            if (bankName.isNotEmpty() && accountNumber.isNotEmpty()) {
                registerAccount(bankName, accountNumber)
            } else {
                Toast.makeText(this, "은행과 계좌번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, AccountVerificationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onBankSelected(bankName: String, bankImageRes: Int) {
        bankSelector.text = bankName
        bankSelector.setCompoundDrawablesWithIntrinsicBounds(0, 0, bankImageRes, 0)
    }

    private fun registerAccount(bankName: String, accountNumber: String) {
        val apiService = RetrofitInstance.api
        val request = AccountRegisterRequest(bankName, accountNumber)

        apiService.registerAccount(request).enqueue(object : Callback<ResultResponseVoid> {
            override fun onResponse(call: Call<ResultResponseVoid>, response: Response<ResultResponseVoid>) {
                if (response.isSuccessful) {
                    Log.d("AccountInfoInputActivity", "계좌등록 Success")
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "계좌 등록에 실패했습니다."
                    Log.d("AccountInfoInputActivity", "$bankName, $accountNumber")
                    Log.e("AccountInfoInputActivity", "계좌등록 Failed: $errorMessage")
                }
            }

            override fun onFailure(call: Call<ResultResponseVoid>, t: Throwable) {
                Log.e("AccountInfoInputActivity", "Error: ${t.message}")
            }
        })
    }

}