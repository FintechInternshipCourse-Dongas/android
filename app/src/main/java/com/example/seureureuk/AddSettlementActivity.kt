package com.example.seureureuk

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupMemberResponse
import com.example.seureureuk.data.model.createSettlementAddRequest
import com.example.seureureuk.ui.viewmodel.SettlementViewModel
import java.util.Calendar

class AddSettlementActivity : AppCompatActivity() {

    private val settlementViewModel: SettlementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_settlement)

        val settlementNameEditText: EditText = findViewById(R.id.settlement_name_edit_text)
        settlementNameEditText.addTextChangedListener(createTextWatcher(settlementNameEditText))

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
            // settlement 생성을 위한 입력값 받아오기
            val settlementName = settlementNameEditText.text.toString()
            val totalAmount = totalAmountEditText.text.toString().toInt()
            val meetingDate = meetingDateEditText.text.toString()
            val settlementDate = settlementDateEditText.text.toString()
            val location = locationEditText.text.toString()

            val meetingDateParts = meetingDate.split(".")
            val settlementDateParts = settlementDate.split(".")

            val formattedMeetingDate = String.format("%04d-%02d-%02d",
                meetingDateParts[0].toInt(), // 년도는 4자리
                meetingDateParts[1].toInt(),
                meetingDateParts[2].toInt()
            )

            val formattedSettlementDate = String.format("%04d-%02d-%02d",
                settlementDateParts[0].toInt(), // 년도는 4자리
                settlementDateParts[1].toInt(),
                settlementDateParts[2].toInt()
            )

            val request = createSettlementAddRequest(settlementName, totalAmount, formattedMeetingDate,
                formattedSettlementDate, location, groupMembers)

            // settlement 추가 POST 요청
            settlementViewModel.addSettlement(groupId, request)
            settlementViewModel.addSettlementResponse.observe(this) { response ->
                val newSettlementId = response.data.id // 생성된 settlementId 받기

                // 생성된 settlementId로 정산 동의 여부 조회
                settlementViewModel.getSettlementParticipants(newSettlementId)
                settlementViewModel.getSettlementParticipantsResponse.observe(this) { response ->
                    val intent = Intent(this, RequestSettlementActivity::class.java)
                    val participants = response.data
                    intent.putParcelableArrayListExtra("participants", ArrayList(participants))
                    intent.putExtra("settlementId", newSettlementId)
                    startActivity(intent)
                    finish()
                }
            }

            settlementViewModel.error.observe(this) { errorMessage ->
                Log.e("AddSettlementActivity", errorMessage)
            }
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
