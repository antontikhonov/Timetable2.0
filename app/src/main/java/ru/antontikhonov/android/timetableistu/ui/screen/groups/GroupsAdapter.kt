package ru.antontikhonov.android.timetableistu.ui.screen.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.antontikhonov.android.timetableistu.R

class GroupsAdapter(val onGroupClick: (String) -> Unit) :
    ListAdapter<String, GroupsAdapter.GroupsViewHolder>(StringDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GroupsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val groupNumber = view.findViewById<TextView>(R.id.groupNumber)

        fun bind(group: String) {
            groupNumber.text = group
            groupNumber.setOnClickListener {
                onGroupClick(group)
            }
        }
    }

    object StringDiffUtil : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
