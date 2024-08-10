package com.example.seureureuk

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupMemberResponse
import java.util.Calendar

class AddSettlementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_settlement)

        val storeNameEditText: EditText = findViewById(R.id.store_name_edit_text)
        storeNameEditText.addTextChangedListener(createTextWatcher(storeNameEditText))

        val totalAmountEditText: EditText = findViewById(R.id.total_amount_edit_text)
        totalAmountEditText.addTextChangedListener(createTextWatcher(totalAmountEditText))

        val meetingDateEditText: EditText = findViewById(R.id.meeting_date_edit_text)
        meetingDateEditText.setOnClickListener {
            showDatePickerDialog(meetingDateEditText)
        }
        meetingDateEditText.addTextChangedListener(createTextWatcher(meetingDateEditText))

        val settlementDateEditText: EditText = findViewById(R.id.settlement_date_edit_text)
        settlementDateEditText.setOnClickListener {
            showDatePickerDialog(settlementDateEditText)
        }
        settlementDateEditText.addTextChangedListener(createTextWatcher(settlementDateEditText))

        val locationEditText: EditText = findViewById(R.id.location_edit_text)
        locationEditText.addTextChangedListener(createTextWatcher(locationEditText))

        // RecyclerView 설정
        val membersLayout = findViewById<RecyclerView>(R.id.member_recycler_view)
        membersLayout.layoutManager = LinearLayoutManager(this) // LayoutManager 설정

        val groupMembers = intent.getParcelableArrayListExtra<GroupMemberResponse>("groupMembers") ?: arrayListOf()
        val groupId = intent.getIntExtra("groupId", -1) // !!!나중에 add settlement 버튼 누를 때 넘겨줘야 함!!!

        val memberAdapter = AddSettlementMemberAdapter(groupMembers) { member ->
            // 클릭 이벤트 처리
        }
        membersLayout.adapter = memberAdapter

        val selectAllCheckbox: CheckBox = findViewById(R.id.select_all_checkbox)
        selectAllCheckbox.setOnCheckedChangeListener { _, isChecked ->
            groupMembers.forEach { it.isSelected = isChecked }
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

    private fun createTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.setTextColor(
                    if (s.isNullOrEmpty()) ContextCompat.getColor(this@AddSettlementActivity, R.color.gray)
                    else ContextCompat.getColor(this@AddSettlementActivity, R.color.black)
                )
            }

            override fun afterTextChanged(s: Editable?) {}
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
