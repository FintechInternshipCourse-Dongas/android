package com.example.seureureuk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupSettlementResponse

class GroupSettlementAdapter(
    private var groupSettlements: List<GroupSettlementResponse>,
    private val context: Context
) : RecyclerView.Adapter<GroupSettlementAdapter.GroupSettlementViewHolder>() {

    fun updateData(newSettlements: List<GroupSettlementResponse>) {
        groupSettlements = newSettlements
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupSettlementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_settlement_item, parent, false)
        return GroupSettlementViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupSettlementViewHolder, position: Int) {
        holder.bind(groupSettlements[position])
    }

    override fun getItemCount() = groupSettlements.size

    inner class GroupSettlementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val settlementRecyclerView: RecyclerView = itemView.findViewById(R.id.settlement_recycler_view)

        fun bind(groupSettlement: GroupSettlementResponse) {
            // 하위 어댑터를 사용하여 정산 항목 표시
            val settlementAdapter = SubSettlementAdapter(groupSettlement.settlements)
            settlementRecyclerView.layoutManager = LinearLayoutManager(context)
            settlementRecyclerView.adapter = settlementAdapter
        }
    }
}
