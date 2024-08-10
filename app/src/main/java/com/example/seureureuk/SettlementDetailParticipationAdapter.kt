package com.example.seureureuk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.Participant

class SettlementDetailParticipationAdapter(
    private val context: Context,
    private val memberList: List<Participant>
) : RecyclerView.Adapter<SettlementDetailParticipationAdapter.MemberViewHolder>() {

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memberName: TextView = itemView.findViewById(R.id.member_name)
        val memberAmount: TextView = itemView.findViewById(R.id.member_amount)
        val memberAvatar: ImageView = itemView.findViewById(R.id.member_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.settlement_participation_item, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentMember = memberList[position]
        holder.memberName.text = currentMember.participantName
        holder.memberAmount.text = currentMember.paymentAmount.toString()

        val icNum = (currentMember.id % 6) + 1
        val resourceId = context.resources.getIdentifier("ic_member_${icNum}", "drawable", context.packageName)
        if (resourceId != 0) {
            holder.memberAvatar.setImageResource(resourceId)
        } else {
            holder.memberAvatar.setImageResource(R.drawable.ic_member_1) // 기본 이미지 설정
        }
    }

    override fun getItemCount() = memberList.size
}
