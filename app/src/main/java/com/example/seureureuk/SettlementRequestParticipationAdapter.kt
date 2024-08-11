package com.example.seureureuk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.SettlementParticipantResponse

class SettlementRequestParticipationAdapter(
    private val context: Context,
    private val participants: List<SettlementParticipantResponse>?
) : RecyclerView.Adapter<SettlementRequestParticipationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val memberAvatar: ImageView = view.findViewById(R.id.memberAvatar)
        val memberName: TextView = view.findViewById(R.id.memberName)
        val requestStatus: TextView = view.findViewById(R.id.requestStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.settlement_request_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val participant = participants?.get(position)

        if (participant != null) {
            holder.memberName.text = participant.participantName
            holder.requestStatus.text = if (participant.agreementStatus) "동의 완료" else "요청 보냄"

            holder.requestStatus.setTextColor(
                if (participant.agreementStatus) context.getColor(R.color.consent_complete) else context.getColor(
                    R.color.consent_waiting
                )
            )

            val icNum = (participant.id % 6) + 1
            val resourceId = context.resources.getIdentifier("ic_member_${icNum}", "drawable", context.packageName)
            if (resourceId != 0) {
                holder.memberAvatar.setImageResource(resourceId)
            } else {
                holder.memberAvatar.setImageResource(R.drawable.ic_member_1) // 기본 이미지 설정
            }
        }
    }

    override fun getItemCount(): Int {
        return participants?.size ?: 0
    }
}
