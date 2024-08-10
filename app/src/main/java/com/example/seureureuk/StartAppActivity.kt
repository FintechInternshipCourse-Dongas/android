package com.example.seureureuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.seureureuk.data.model.GroupInfoResponse
import com.example.seureureuk.ui.viewmodel.GroupViewModel

class StartAppActivity : AppCompatActivity() {

    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_start)

        val signUpBtn = findViewById<Button>(R.id.start_sign_up_button)
        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val kakaoBtn = findViewById<Button>(R.id.start_kakao_button)
        kakaoBtn.setOnClickListener {
            groupViewModel.groups.observe(this, Observer { groups ->
                if (groups != null) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putParcelableArrayListExtra("groups", ArrayList(groups))
                    startActivity(intent)
                }
            })

            groupViewModel.error.observe(this, Observer { error ->
                if (error != null) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putParcelableArrayListExtra("groups", ArrayList<GroupInfoResponse>())
                    startActivity(intent)
                }
            })

            groupViewModel.fetchAllGroups()
        }

        val loginBtn = findViewById<Button>(R.id.start_login_button)
        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
