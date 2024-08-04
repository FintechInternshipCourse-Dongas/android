package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로고
        val logo = findViewById<ImageView>(R.id.logo)
        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 모임 생성 버튼
        val createMeetingBtn = findViewById<ImageView>(R.id.button_add)
        createMeetingBtn.setOnClickListener {
            // 모임 생성 버튼 클릭 시 MeetingCreationActivity로 화면 전환
            val intent = Intent(this, CreateGroupActivity::class.java)
            startActivity(intent)
        }

        // 네비게이션 홈 버튼
        val navigationHome = findViewById<LinearLayout>(R.id.navigation_home)
        navigationHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 네비게이션 MY 버튼
        val navigationMy = findViewById<LinearLayout>(R.id.navigation_my)
        navigationMy.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}