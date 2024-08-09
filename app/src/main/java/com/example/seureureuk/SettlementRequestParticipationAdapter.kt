package com.example.seureureuk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.SettlementParticipation

class SettlementRequestParticipationAdapter(
    private val context: Context,
    private val members: List<SettlementParticipation>
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
        val member = members[position]
        holder.memberName.text = member.name
        holder.requestStatus.text = if (member.status == true) "동의 완료" else "요청 보냄"

        holder.requestStatus.setTextColor(
            if (member.status == true) context.getColor(R.color.consent_complete) else context.getColor(
                R.color.consent_waiting
            )
        )

        holder.memberAvatar.setImageResource(R.drawable.ic_member)
    }

    override fun getItemCount(): Int {
        return members.size
    }
}
