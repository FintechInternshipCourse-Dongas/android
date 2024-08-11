package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.ResultResponsePointInfoResponse
import com.example.seureureuk.data.model.ResultResponseUserMyPageResponse
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class MyPageActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var balanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        nameTextView = findViewById(R.id.user_name)
        balanceTextView = findViewById(R.id.balance_amount)

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val userInfoBtn = findViewById<Button>(R.id.manage_info_button)
        userInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPageUserInfoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val sendBtn = findViewById<Button>(R.id.send_button)
        sendBtn.setOnClickListener {
            val intent = Intent(this, ShowAccountActivity::class.java)
            startActivity(intent)
        }

        val rechargeBtn = findViewById<Button>(R.id.recharge_button)
        rechargeBtn.setOnClickListener {
            val intent = Intent(this, ChargePointActivity::class.java)
            startActivity(intent)
        }

        val paymentInfoBtn = findViewById<ImageView>(R.id.payment_management_arrow)
        paymentInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPagePaymentActivity::class.java)
            startActivity(intent)
        }
        val paymentLayoutButton: RelativeLayout = findViewById(R.id.payment_management)
        paymentLayoutButton.setOnClickListener {
            val intent = Intent(this, MyPagePaymentActivity::class.java)
            startActivity(intent)
        }

        val groupInfoBtn = findViewById<ImageView>(R.id.group_management_arrow)
        groupInfoBtn.setOnClickListener {
            val intent = Intent(this, MyPageGroupActivity::class.java)
            startActivity(intent)
        }
        val groupLayoutButton: RelativeLayout = findViewById(R.id.group_management)
        groupLayoutButton.setOnClickListener {
            val intent = Intent(this, MyPageGroupActivity::class.java)
            startActivity(intent)
        }

        loadUserInfo()
        loadUserPoints()
    }

    private fun loadUserInfo() {
        val apiService = RetrofitInstance.api
        apiService.getUserMyPageInfo().enqueue(object : Callback<ResultResponseUserMyPageResponse> {
            override fun onResponse(call: Call<ResultResponseUserMyPageResponse>, response: Response<ResultResponseUserMyPageResponse>) {
                if (response.isSuccessful) {
                    val userInfo = response.body()?.data
                    if (userInfo != null) {
                        nameTextView.text = userInfo.name
                        Log.d("MyPageActivity","$userInfo.name")
                    } else {
                        Toast.makeText(this@MyPageActivity, "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("MyPageActivity", "Failed to load user info: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResultResponseUserMyPageResponse>, t: Throwable) {
                Log.e("MyPageActivity", "Error: ${t.message}")
            }
        })
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
                        Log.d("MyPageActivity", "$pointInfo")
                    } else {
                        Toast.makeText(this@MyPageActivity, "포인트 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("MyPageActivity", "Failed to load points info: $errorBody")
                }
            }

            override fun onFailure(call: Call<ResultResponsePointInfoResponse>, t: Throwable) {
                Log.e("MyPageActivity", "Network error: ${t.message}")
            }
        })
    }
}