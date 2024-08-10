package com.example.seureureuk

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupSettlementResponse

class SubSettlementAdapter(
    private val settlements: List<GroupSettlementResponse>
) : RecyclerView.Adapter<SubSettlementAdapter.SubSettlementViewHolder>() {

    class SubSettlementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val settlementName: TextView = itemView.findViewById(R.id.settlement_name)
        val settlementAmount: TextView = itemView.findViewById(R.id.settlement_amount)
        val settlementDate: TextView = itemView.findViewById(R.id.settlement_date)

        fun bind(settlement: GroupSettlementResponse) {
            settlementName.text = settlement.settlementName
            settlementAmount.text = "${settlement.totalPaymentAmount}원"
            settlementDate.text = settlement.settlementAt

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, SettlementDetailActivity::class.java)
                intent.putExtra("settlementId", settlement.id)  // settlement의 ID 전달
                context.startActivity(intent)
            }
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
