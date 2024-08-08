package com.example.seureureuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.Settlement

class SettlementAdapter(private val settlements: List<Settlement>) : RecyclerView.Adapter<SettlementAdapter.SettlementViewHolder>() {

    class SettlementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionName: TextView = itemView.findViewById(R.id.settlement_name)
        val transactionAmount: TextView = itemView.findViewById(R.id.settlement_amount)
        val transactionDate: TextView = itemView.findViewById(R.id.settlement_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettlementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.settlement_item, parent, false)
        return SettlementViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettlementViewHolder, position: Int) {
        val settlement = settlements[position]
        holder.transactionName.text = settlement.name
        holder.transactionAmount.text = "${settlement.amount}원"
        holder.transactionDate.text = settlement.date
    }

    override fun getItemCount(): Int {
        return settlements.size
    }
}
