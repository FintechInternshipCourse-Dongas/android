package com.example.seureureuk

import android.app.Application
import com.example.seureureuk.network.RetrofitInstance

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitInstance.initialize(this)
    }
}
