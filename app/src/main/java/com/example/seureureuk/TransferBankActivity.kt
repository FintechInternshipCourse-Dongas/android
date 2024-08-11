package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.WindowInsetsAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.PointConversionRequest
import com.example.seureureuk.data.model.ResultResponsePointConversionResponse
import com.example.seureureuk.data.model.ResultResponseUserMyPageResponse
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class TransferBankActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_bank)

        val backBtn = findViewById<ImageView>(R.id.back_button)
        val editTextAmount = findViewById<EditText>(R.id.edit_transfer_amount)
        val buttonConvert = findViewById<Button>(R.id.confirm_button)

        backBtn.setOnClickListener {
            Log.d("TransferBankActivity", "Back button clicked, navigating to ShowAccountActivity")
            val intent = Intent(this, ShowAccountActivity::class.java)
            startActivity(intent)
        }

        buttonConvert.setOnClickListener {
            val amountString = editTextAmount.text.toString()
            val amount = amountString.toIntOrNull()

            if (amount != null) {
                val request = PointConversionRequest(amount)
                convertPointsExchange(amount)
            } else {
                Toast.makeText(this, "유효한 금액을 입력하세요", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, FinishTransferActivity::class.java)
            intent.putExtra("amount", amount.toString())  // Pass amount as a String
            startActivity(intent)
        }
    }

    private fun convertPointsExchange(amount: Int) {
        Log.d("TransferBankActivity", "Starting point conversion with amount: $amount")
        val apiService = RetrofitInstance.api
        val request = PointConversionRequest(amount)

        apiService.convertPointsExchange(request).enqueue(object : Callback<ResultResponsePointConversionResponse> {
            override fun onResponse(call: retrofit2.Call<ResultResponsePointConversionResponse>, response: Response<ResultResponsePointConversionResponse>) {
                if (response.isSuccessful) {
                    val conversionResponse = response.body()?.data
                    if (conversionResponse != null) {
                        Log.d("TransferBankActivity", "Point conversion successful: $conversionResponse")
                    } else {
                        Toast.makeText(this@TransferBankActivity, "응답 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "알 수 없는 오류"
                    Log.e("TransferBankActivity", "Failed to convert points. Error: $errorMessage")
                    Toast.makeText(this@TransferBankActivity, "포인트 충전에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: retrofit2.Call<ResultResponsePointConversionResponse>, t: Throwable) {
                Log.e("ChargePointActivity", "Network failure: ${t.message}")
                Toast.makeText(this@TransferBankActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }













}