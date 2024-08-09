package com.example.seureureuk.network

import android.content.Context
import android.util.Log
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://172.191.5.168/api/"
    private var token: String? = null

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthInterceptor(token))
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build()
        }

    val api: ApiService
        get() {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)  // 매번 새로운 OkHttpClient를 생성
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    fun updateToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String? {
        return token
    }

    fun initializeToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedToken = sharedPreferences.getString("auth_token", null)
        if (!savedToken.isNullOrEmpty()) {
            updateToken(savedToken)
            Log.d("RetrofitInstance", "Token loaded from SharedPreferences: $savedToken")
        } else {
            Log.e("RetrofitInstance", "No token found in SharedPreferences")
        }
    }
}