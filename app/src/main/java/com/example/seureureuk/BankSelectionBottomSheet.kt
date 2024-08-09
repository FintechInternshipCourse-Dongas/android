package com.example.seureureuk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BankSelectionBottomSheet : BottomSheetDialogFragment() {

    interface OnBankSelectedListener {
        fun onBankSelected(bankName: String, bankImageRes: Int)
    }

    private var listener: OnBankSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is OnBankSelectedListener) {
            context
        } else {
            throw RuntimeException("$context must implement OnBankSelectedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_YourApp_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bank_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.kakao_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.kakaobank), R.drawable.ic_kakao_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.nh_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.nh), R.drawable.ic_nh_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.kb_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.kb), R.drawable.ic_kb_bank)
            dismiss()
        }

        // 다른 은행 버튼들에 대해서도 같은 방식으로 설정
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}