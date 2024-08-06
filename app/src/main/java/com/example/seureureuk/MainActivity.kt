package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val logo = findViewById<ImageView>(R.id.logo)
//        logo.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        val createGroupBtn = findViewById<ImageView>(R.id.button_add)
        createGroupBtn.setOnClickListener {
            val intent = Intent(this, CreateGroupActivity::class.java)
            startActivity(intent)
        }

        val createGroupView = findViewById<CardView>(R.id.add_group_button)
        createGroupView.setOnClickListener {
            val intent = Intent(this, CreateGroupActivity::class.java)
            startActivity(intent)
        }

//        val navigationHome = findViewById<LinearLayout>(R.id.navigation_home)
//        navigationHome.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        val navigationMy = findViewById<LinearLayout>(R.id.navigation_my)
        navigationMy.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}