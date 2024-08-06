package com.example.seureureuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SettlementParticipationAdapter(
    private val memberList: List<SettlementParticipation>,
    private val onItemClick: (SettlementParticipation) -> Unit
) : RecyclerView.Adapter<SettlementParticipationAdapter.MemberViewHolder>() {

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memberName: TextView = itemView.findViewById(R.id.memberName)
        val memberAmount: TextView = itemView.findViewById(R.id.memberAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.settlement_participation_item, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentMember = memberList[position]
        holder.memberName.text = currentMember.name
        holder.memberAmount.text = currentMember.amount
        holder.itemView.setBackgroundColor(
            if (currentMember.isSelected == true)
                ContextCompat.getColor(holder.itemView.context, R.color.selected_background)
            else
                ContextCompat.getColor(holder.itemView.context, R.color.default_background)
        )

        holder.itemView.setOnClickListener {
            currentMember.isSelected = !currentMember.isSelected!!
            notifyItemChanged(position)
            onItemClick(currentMember)
        }
    }

    override fun getItemCount() = memberList.size
}
