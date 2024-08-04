package com.example.seureureuk

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle Home navigation
                    true
                }
                R.id.navigation_my -> {
                    // Handle MY navigation
                    true
                }
                else -> false
            }
        }

        val favoriteGroupContainer = findViewById<LinearLayout>(R.id.favoriteGroupContainer)
        val groupContainer = findViewById<LinearLayout>(R.id.groupContainer)

        val favoriteGroups = listOf(
            GroupData("핀인코 - 돈가스", "2024.07.13", 6, true)
        )

        val groups = listOf(
            GroupData("HNRC", "2024.08.22", 10),
            GroupData("핀인코 - 돈가스", "2024.07.13", 6, true),
            GroupData("개발 2팀", "2024.04.10", 8),
            GroupData("조김이최", "2024.01.20", 4)
        )

        favoriteGroups.forEach {
            favoriteGroupContainer.addView(createGroupCard(it))
        }

        groups.forEach {
            groupContainer.addView(createGroupCard(it))
        }
    }

    private fun createGroupCard(group: GroupData): CardView {
        val cardView = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 4, 0, 4)
            }
            radius = 10f
            cardElevation = 4f
        }

        val cardContent = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
        }

        val textContent = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            weightSum = 1f
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val nameTextView = TextView(this).apply {
            text = group.name
            textSize = 16f
            setTextColor(Color.BLACK)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val dateTextView = TextView(this).apply {
            text = group.date
            textSize = 14f
            setTextColor(Color.GRAY)
        }

        val membersTextView = TextView(this).apply {
            text = "멤버: ${group.members}명"
            textSize = 14f
            setTextColor(Color.GRAY)
        }

        textContent.addView(nameTextView)
        textContent.addView(dateTextView)
        textContent.addView(membersTextView)

        val button = Button(this).apply {
            text = "정산 하기"
            setBackgroundColor(Color.parseColor("#6200EE"))
            setTextColor(Color.WHITE)
        }

        cardContent.addView(textContent)
        if (group.isFavorite) {
            val starTextView = TextView(this).apply {
                text = "★"
                textSize = 24f
                setTextColor(Color.YELLOW)
            }
            cardContent.addView(starTextView)
        }
        cardContent.addView(button)

        cardView.addView(cardContent)

        return cardView
    }

    data class GroupData(
        val name: String,
        val date: String,
        val members: Int,
        val isFavorite: Boolean = false
    )
}