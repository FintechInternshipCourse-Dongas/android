package com.example.seureureuk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.ResultResponseUserLoginResponse
import com.example.seureureuk.data.model.UserLoginRequest
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.idInput)
        val passwordEditText = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            Log.d("LoginActivity", "Collected user data: $email, $password")

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = UserLoginRequest(email, password)
                RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<ResultResponseUserLoginResponse> {
                    override fun onResponse(call: Call<ResultResponseUserLoginResponse>, response: Response<ResultResponseUserLoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse?.data != null) {
                                val token = loginResponse.data.accessToken
                                RetrofitInstance.updateToken(token)

                                // 토큰을 SharedPreferences에 저장
                                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("auth_token", token)
                                editor.apply()

                                val intent = Intent(this@LoginActivity, PaymentMethodRegistrationActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.e("LoginActivity", "Login Failed: ${loginResponse?.message}")
                                Toast.makeText(this@LoginActivity, "Login Failed: ${loginResponse?.message}", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.e("LoginActivity", "Login Failed: ${response.errorBody()?.string()}")
                            Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResultResponseUserLoginResponse>, t: Throwable) {
                        Log.e("LoginActivity", "Error: ${t.message}")
                        Toast.makeText(this@LoginActivity, "Login Error", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}