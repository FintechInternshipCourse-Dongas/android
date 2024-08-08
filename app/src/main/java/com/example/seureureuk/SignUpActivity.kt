package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.data.model.UserSignUpRequest
import com.example.seureureuk.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    private lateinit var selectedTelecomText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.seureureuk.R.layout.activity_sign_up)

        val emailEditText = findViewById<EditText>(com.example.seureureuk.R.id.idInput)
        val passwordEditText = findViewById<EditText>(com.example.seureureuk.R.id.passwordInput)
        val nameEditText = findViewById<EditText>(com.example.seureureuk.R.id.nameInput)
        val phoneNumberEditText = findViewById<EditText>(com.example.seureureuk.R.id.phoneInput)
        val signUpButton = findViewById<Button>(R.id.confirm_button)

        val backBtn = findViewById<ImageView>(com.example.seureureuk.R.id.back_button)
        backBtn.setOnClickListener {
            val intent = Intent(this, StartAppActivity::class.java)
            startActivity(intent)
        }

        selectedTelecomText = findViewById<TextView>(com.example.seureureuk.R.id.selectedTelecomText)
        selectedTelecomText.setOnClickListener {
            showTelecomSelectionDialog()
        }

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()

            Log.d("SignUpActivity", "Collected user data: $email, $password, $name, $phoneNumber")

            val signUpRequest = UserSignUpRequest(email, password, name, phoneNumber)

            // Make API call
            RetrofitInstance.api.signUpUser(signUpRequest).enqueue(object : Callback<ResultResponseVoid> {
                override fun onResponse(call: Call<ResultResponseVoid>, response: Response<ResultResponseVoid>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("SignUpActivity", "SignUp Success: ${result?.message}")
                    } else {
                        Log.e("SignUpActivity", "SignUp Failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResultResponseVoid>, t: Throwable) {
                    Log.e("SignUpActivity", "Error: ${t.message}")
                }
            })

            val intent = Intent(this, CertificateUserActivity::class.java)
            startActivity(intent)
        }
    }


    private fun showTelecomSelectionDialog() {
        val telecomOptions = arrayOf("SKT", "KT", "LG U+", "SKT 알뜰폰", "KT 알뜰폰", "LG U+ 알뜰폰")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("통신사를 선택해주세요")
        builder.setItems(telecomOptions) { _, which ->
            val selectedTelecom = telecomOptions[which]
            selectedTelecomText.text = "$selectedTelecom"
        }
        builder.setNegativeButton("취소", null)
        builder.show()
    }
}