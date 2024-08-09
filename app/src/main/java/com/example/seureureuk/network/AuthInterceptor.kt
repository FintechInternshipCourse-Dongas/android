package com.example.seureureuk.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.header("Authorization", "Bearer $token")
            Log.d("AuthInterceptor", "Authorization: Bearer $token")
        } else {
            Log.e("AuthInterceptor", "Token is null or empty!")
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