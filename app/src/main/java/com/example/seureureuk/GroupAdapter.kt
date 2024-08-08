package com.example.seureureuk.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.AddSettlementActivity
import com.example.seureureuk.R
import com.example.seureureuk.SettlementListActivity
import com.example.seureureuk.data.model.GroupInfoResponse

class GroupAdapter(
    private var groups: List<GroupInfoResponse>,
    private val context: Context
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    fun updateData(newGroups: List<GroupInfoResponse>) {
        groups = newGroups
        notifyDataSetChanged()
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
            groupName.text = group.groupName
            groupDate.text = group.createAt.replace("-", ".")
            groupMembers.text = "멤버 : ${group.numOfparticipantCount}명"

            itemView.setOnClickListener {
                val intent = Intent(context, SettlementListActivity::class.java)
                intent.putExtra("group_id", group.id)
                context.startActivity(intent)
            }

            settleButton.setOnClickListener {
                val intent = Intent(context, AddSettlementActivity::class.java)
                intent.putExtra("group_id", group.id)
                context.startActivity(intent)
            }
        }
    }
}
