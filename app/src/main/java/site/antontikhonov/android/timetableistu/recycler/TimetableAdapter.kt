package site.antontikhonov.android.timetableistu.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import site.antontikhonov.android.timetableistu.R
import site.antontikhonov.android.timetableistu.pojo.PairClassEntity

class TimetableAdapter :
    ListAdapter<PairClassEntity, TimetableAdapter.PairClassViewHolder>(PairClassDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairClassViewHolder =
        PairClassViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pair_class, parent, false)
        )

    override fun onBindViewHolder(holder: PairClassViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class PairClassViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val pairNumberTextView = view.findViewById<TextView>(R.id.pairNumber)
        private val oddClassTextView = view.findViewById<TextView>(R.id.oddClass)
        private val evenClassTextView = view.findViewById<TextView>(R.id.evenClass)
        private val decorationView = view.findViewById<View>(R.id.decoration)

        fun bind(timetableElement: PairClassEntity) {
            pairNumberTextView.text = timetableElement.time

            decorationView.isVisible =
                !(timetableElement.odd == null || timetableElement.even == null)
            oddClassTextView.isVisible = timetableElement.odd != null
            oddClassTextView.text = timetableElement.odd

            evenClassTextView.isVisible = timetableElement.even != null
            evenClassTextView.text = timetableElement.even
        }
    }

    object PairClassDiffUtil : DiffUtil.ItemCallback<PairClassEntity>() {
        override fun areItemsTheSame(oldItem: PairClassEntity, newItem: PairClassEntity): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: PairClassEntity, newItem: PairClassEntity): Boolean =
            oldItem == newItem
    }
}