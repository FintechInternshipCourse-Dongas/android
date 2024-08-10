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

        view.findViewById<Button>(R.id.toss_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.tossbank), R.drawable.ic_toss_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.shinhan_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.shinhan), R.drawable.ic_shinhan_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.woori_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.woori), R.drawable.ic_woori_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.ibk_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.ibk), R.drawable.ic_ibk_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.hana_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.hana_bank), R.drawable.ic_hana_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.mg_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.mg_bank), R.drawable.ic_mg_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.bnk_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.bnk_bank), R.drawable.ic_mg_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.k_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.k_bank), R.drawable.ic_k_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.shinhyup_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.shinhyup_bank), R.drawable.ic_shinhyup_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.epost_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.epost_bank), R.drawable.ic_epost_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.suhyup_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.suhyup_bank), R.drawable.ic_suhyup_bank)
            dismiss()
        }

        view.findViewById<Button>(R.id.jeju_bank_button).setOnClickListener {
            listener?.onBankSelected(getString(R.string.jeju_bank), R.drawable.ic_jeju_bank)
            dismiss()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}