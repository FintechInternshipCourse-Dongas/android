package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.ResultResponseUserMyPageResponse
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageUserInfoActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_userinfo)

        nameTextView = findViewById(R.id.name_value)
        phoneTextView = findViewById(R.id.phone_value)

        val backBtn = findViewById<ImageView>(R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        loadUserInfo()
    }

    private fun loadUserInfo() {
        val apiService = RetrofitInstance.api
        apiService.getUserMyPageInfo().enqueue(object : Callback<ResultResponseUserMyPageResponse> {
            override fun onResponse(call: Call<ResultResponseUserMyPageResponse>, response: Response<ResultResponseUserMyPageResponse>) {
                if (response.isSuccessful) {
                    val userInfo = response.body()?.data
                    if (userInfo != null) {
                        nameTextView.text = userInfo.name
                        phoneTextView.text = formatPhoneNumber(userInfo.phoneNumber ?: "")
                        Log.d("MyPageUserInfoActivity","$userInfo.name, $userInfo.phone")
                    } else {
                        Toast.makeText(this@MyPageUserInfoActivity, "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("MyPageUserInfoActivity", "Failed to load user info: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResultResponseUserMyPageResponse>, t: Throwable) {
                Log.e("MyPageUserInfoActivity", "Error: ${t.message}")
            }
        })
    }

    private fun formatPhoneNumber(phone: String): String {
        return if (phone.length == 10) {
            "${phone.substring(0, 3)}-${phone.substring(3, 6)}-${phone.substring(6)}"
        } else if (phone.length == 11) {
            "${phone.substring(0, 3)}-${phone.substring(3, 7)}-${phone.substring(7)}"
        } else {
            phone
        }
    }
}