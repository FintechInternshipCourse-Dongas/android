package com.example.seureureuk

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.seureureuk.data.model.GroupInfoResponse
import com.example.seureureuk.ui.viewmodel.GroupViewModel

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
                val viewModel = ViewModelProvider(context as MainActivity).get(GroupViewModel::class.java)

                // 그룹 멤버와 정산 내역을 가져온 후, 데이터를 전달
                viewModel.fetchGroupMembers(group.id)
                viewModel.fetchGroupSettlements(group.id)

                viewModel.groupMembers.observe(context) { membersResponse ->
                    if (membersResponse != null) {
                        viewModel.groupSettlements.observe(context) { settlementsResponse ->
                            if (settlementsResponse != null) {
                                val intent = Intent(context, SettlementListActivity::class.java)
                                intent.putExtra("groupMembers", ArrayList(membersResponse.data))
                                intent.putExtra("groupSettlements", ArrayList(settlementsResponse.data))
                                intent.putExtra("groupId", group.id)
                                context.startActivity(intent)
                            } else {
                                Log.d("GroupAdapter", "정산 내역이 존재하지 않습니다.")
                            }
                        }
                    } else {
                        Log.d("GroupAdapter", "멤버 목록을 불러오는 데 실패했습니다.")
                    }
                }
            }

            settleButton.setOnClickListener {
                val intent = Intent(context, AddSettlementActivity::class.java)
                intent.putExtra("group_id", group.id)
                context.startActivity(intent)
            }
        }
    }
}
