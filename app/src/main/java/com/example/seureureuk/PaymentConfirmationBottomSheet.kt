package com.example.seureureuk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.seureureuk.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentConfirmationBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_USER_NAME = "user_name"
        private const val ARG_GROUP_NAME = "group_name"
        private const val ARG_REQUESTED_AMOUNT = "requested_amount"
        private const val ARG_SETTLEMENT_NAME = "settlement_name"
        private const val ARG_GROUPING_AT = "grouping_at"
        private const val ARG_TOTAL_AMOUNT = "total_amount"
        private const val ARG_PARTICIPANT_ID = "participant_id"

        fun newInstance(
            userName: String, groupName: String, requestedAmount: Int,
            settlementName: String, groupingAt: String, totalAmount: Int, participantId: Int): PaymentConfirmationBottomSheet {
            val fragment = PaymentConfirmationBottomSheet()
            val args = Bundle().apply {
                putString(ARG_USER_NAME, userName)
                putString(ARG_GROUP_NAME, groupName)
                putInt(ARG_REQUESTED_AMOUNT, requestedAmount)
                putString(ARG_SETTLEMENT_NAME, settlementName)
                putString(ARG_GROUPING_AT, groupingAt)
                putInt(ARG_TOTAL_AMOUNT, totalAmount)
                putInt(ARG_PARTICIPANT_ID, participantId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = arguments?.getString(ARG_USER_NAME)
        val groupName = arguments?.getString(ARG_GROUP_NAME)
        val requestedAmount = arguments?.getInt(ARG_REQUESTED_AMOUNT)
        val settlementName = arguments?.getString(ARG_SETTLEMENT_NAME)
        val groupingAt = arguments?.getString(ARG_GROUPING_AT)
        val totalAmount = arguments?.getInt(ARG_TOTAL_AMOUNT)
        val participantId = arguments?.getInt(ARG_PARTICIPANT_ID)

        val tvUserName: TextView = view.findViewById(R.id.user_name)
        val tvGroupName: TextView = view.findViewById(R.id.group_name)
        val tvRequestedAmount: TextView = view.findViewById(R.id.requested_amount)
        val tvSettlementName: TextView = view.findViewById(R.id.settlement_name)
        val tvGroupingAt: TextView = view.findViewById(R.id.grouping_at)
        val tvTotalAmount: TextView = view.findViewById(R.id.total_amount)

        tvUserName.text = "${userName}님"
        tvGroupName.text = "${groupName}에서 정산을 요청하였습니다."
        tvRequestedAmount.text = "${requestedAmount ?: 0}원"
        tvSettlementName.text = settlementName
        tvGroupingAt.text = groupingAt
        tvTotalAmount.text = "${totalAmount ?: 0}원"

        val agreeButton = view.findViewById<Button>(R.id.btn_agree)
        agreeButton.setOnClickListener {
            

            dismiss()
        }
    }
}
