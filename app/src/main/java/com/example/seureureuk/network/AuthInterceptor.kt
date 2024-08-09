package com.example.seureureuk.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        // SharedPreferences에서 토큰을 가져옴
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        // 로그인 요청이 아닌 경우에만 헤더에 토큰을 추가
        if (token != null && !original.url.encodedPath.contains("/users/login")) {
            requestBuilder.header("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()
        return chain.proceed(request)

        // 로그인 요청인지 확인
//        val isLoginRequest = original.url.encodedPath.contains("/users/login")
//
//        if (token != null && !isLoginRequest) {
//            requestBuilder.header("Authorization", "Bearer $token")
//        }
//
//        val request = requestBuilder.build()
//        return chain.proceed(request)
    }
}