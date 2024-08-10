package com.example.seureureuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupMemberResponse
import com.example.seureureuk.data.model.SettlementParticipation

class AddSettlementMemberAdapter(
    private val memberList: ArrayList<GroupMemberResponse>,
    private val onItemClick: (GroupMemberResponse) -> Unit
) : RecyclerView.Adapter<AddSettlementMemberAdapter.MemberViewHolder>() {

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memberName: TextView = itemView.findViewById(R.id.member_name)
        val memberImage: ImageView = itemView.findViewById(R.id.member_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.settlement_participation_item, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentMember = memberList[position]
        holder.memberName.text = currentMember.memberName
        holder.itemView.setBackgroundColor(
            if (currentMember.isSelected == true)
                ContextCompat.getColor(holder.itemView.context, R.color.selected_background)
            else
                ContextCompat.getColor(holder.itemView.context, R.color.default_background)
        )

        val icNum = (position % 6) + 1
        val resourceId = holder.itemView.context.resources.getIdentifier(
            "ic_member_$icNum",
            "drawable",
            holder.itemView.context.packageName
        )
        holder.memberImage.setImageResource(resourceId)

        holder.itemView.setOnClickListener {
            currentMember.isSelected = !currentMember.isSelected!!
            notifyItemChanged(position)
            onItemClick(currentMember)
        }
    }


    override fun getItemCount() = memberList.size
}
