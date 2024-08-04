package com.example.seureureuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SettlementParticipationAdapter(private val memberList: List<SettlementParticipation>) : RecyclerView.Adapter<SettlementParticipationAdapter.MemberViewHolder>() {

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memberName: TextView = itemView.findViewById(R.id.memberName)
        val memberAmount: TextView = itemView.findViewById(R.id.memberAmount)
        val memberAvatar: ImageView = itemView.findViewById(R.id.memberAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.settlement_participation_item, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentMember = memberList[position]
        holder.memberName.text = currentMember.name
        holder.memberAmount.text = currentMember.amount
        // Optionally set avatar here if needed
        // holder.memberAvatar.setImageResource(R.drawable.some_avatar) if you have dynamic avatars
    }

    override fun getItemCount() = memberList.size
}
