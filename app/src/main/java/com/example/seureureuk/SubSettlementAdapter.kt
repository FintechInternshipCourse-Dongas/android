package com.example.seureureuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupSettlement

class SubSettlementAdapter(private val settlements: List<GroupSettlement>) : RecyclerView.Adapter<SubSettlementAdapter.SubSettlementViewHolder>() {

    class SubSettlementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val settlementName: TextView = itemView.findViewById(R.id.settlement_name)
        val settlementAmount: TextView = itemView.findViewById(R.id.settlement_amount)
        val settlementDate: TextView = itemView.findViewById(R.id.settlement_date)

        fun bind(settlement: GroupSettlement) {
            settlementName.text = settlement.settlementName
            settlementAmount.text = "${settlement.totalPaymentAmount}원"
            settlementDate.text = settlement.settlementAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubSettlementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.settlement_item, parent, false)
        return SubSettlementViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubSettlementViewHolder, position: Int) {
        holder.bind(settlements[position])
    }

    override fun getItemCount(): Int {
        return settlements.size
    }
}
