package com.example.seureureuk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CreateGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity_create_group.xml 레이아웃을 이 Activity의 화면으로 설정
        setContentView(R.layout.activity_create_group)
    }
}