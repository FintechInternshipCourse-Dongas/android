package com.example.seureureuk

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class TermsAgreementActivity : AppCompatActivity() {

    private lateinit var checkCircle: ImageView
    private lateinit var check1: ImageView
    private lateinit var check2: ImageView
    private lateinit var check3: ImageView
    private lateinit var check4: ImageView
    private lateinit var check5: ImageView
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_agreement)

        checkCircle = findViewById(R.id.check_circle)
        check1 = findViewById(R.id.check_1)
        check2 = findViewById(R.id.check_2)
        check3 = findViewById(R.id.check_3)
        check4 = findViewById(R.id.check_4)
        check5 = findViewById(R.id.check_5)
        nextButton = findViewById(R.id.next_button)

        checkCircle.setOnClickListener {
            setAllChecksToFinished()
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, AccountInfoInputActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setAllChecksToFinished() {
        checkCircle.setImageResource(R.drawable.ic_check_circle_completed)
        check1.setImageResource(R.drawable.ic_check_completed)
        check2.setImageResource(R.drawable.ic_check_completed)
        check3.setImageResource(R.drawable.ic_check_completed)
        check4.setImageResource(R.drawable.ic_check_completed)
        check5.setImageResource(R.drawable.ic_check_completed)

        nextButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.consent_complete))
    }
}