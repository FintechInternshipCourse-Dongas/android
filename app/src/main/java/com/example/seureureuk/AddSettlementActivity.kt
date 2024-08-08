package com.example.seureureuk

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class AddSettlementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_settlement)

        val store_name_edit_text: EditText = findViewById(R.id.store_name_edit_text)
        store_name_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                store_name_edit_text.setTextColor(
                    if (s.isNullOrEmpty()) ContextCompat.getColor(this@AddSettlementActivity, R.color.gray)
                    else ContextCompat.getColor(this@AddSettlementActivity, R.color.black)
                )
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val total_amount_edit_text: EditText = findViewById(R.id.total_amount_edit_text)
        total_amount_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                total_amount_edit_text.setTextColor(
                    if (s.isNullOrEmpty()) ContextCompat.getColor(this@AddSettlementActivity, R.color.gray)
                    else ContextCompat.getColor(this@AddSettlementActivity, R.color.black)
                )
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val meetingDateEditText: EditText = findViewById(R.id.meeting_date_edit_text)
        meetingDateEditText.setOnClickListener {
            showDatePickerDialog(meetingDateEditText)
        }

        val settlementDateEditText: EditText = findViewById(R.id.settlement_date_edit_text)
        settlementDateEditText.setOnClickListener {
            showDatePickerDialog(settlementDateEditText)
        }

        val locationEditText: EditText = findViewById(R.id.location_edit_text)
        locationEditText.setText("")

        val members = listOf(
            com.example.seureureuk.data.model.SettlementParticipation("민선", "17,750원"),
            com.example.seureureuk.data.model.SettlementParticipation("가은", "17,750원"),
            com.example.seureureuk.data.model.SettlementParticipation("해성", "17,750원"),
            com.example.seureureuk.data.model.SettlementParticipation("건", "17,750원"),
            com.example.seureureuk.data.model.SettlementParticipation("한비", "17,750원"),
            com.example.seureureuk.data.model.SettlementParticipation("나영", "17,750원")
        )

        val memberAdapter = SettlementParticipationAdapter(members) { member ->

        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = memberAdapter

        val selectAllCheckbox: CheckBox = findViewById(R.id.select_all_checkbox)
        selectAllCheckbox.setOnCheckedChangeListener { _, isChecked ->
            members.forEach { it.isSelected = isChecked }
            memberAdapter.notifyDataSetChanged()
        }

        val addSettlementButton: Button = findViewById(R.id.add_settlement_button)
        addSettlementButton.setOnClickListener {
            val intent = Intent(this, RequestSettlementActivity::class.java)
            startActivity(intent)
            finish()
        }

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "${selectedYear}.${selectedMonth + 1}.${selectedDay}"
                editText.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
