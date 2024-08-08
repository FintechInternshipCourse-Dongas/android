package com.example.seureureuk.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.R
import com.example.seureureuk.data.model.GroupInfoResponse

class GroupAdapter(
    private var groups: List<GroupInfoResponse>,
    private val onItemClick: (GroupInfoResponse) -> Unit
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    fun updateData(newGroups: List<GroupInfoResponse>) {
        groups = newGroups
        notifyDataSetChanged()  // 데이터 세트가 변경되었음을 RecyclerView에 알림
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_item, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position])
    }

    override fun getItemCount() = groups.size

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val groupName: TextView = itemView.findViewById(R.id.text_group_name)
        private val groupDate: TextView = itemView.findViewById(R.id.text_group_date)
        private val groupMembers: TextView = itemView.findViewById(R.id.text_group_members)
        private val settleButton: Button = itemView.findViewById(R.id.settle_button)

        fun bind(group: GroupInfoResponse) {
            groupName.text = group.name
            groupDate.text = group.createAt.toString()
            groupMembers.text = "멤버 : ${group.numOfparticipantCount}"

            itemView.setOnClickListener {
                onItemClick(group)
            }

            settleButton.setOnClickListener {
                // Handle settle button click
            }
        }
    }
}
