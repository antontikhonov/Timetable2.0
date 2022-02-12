package ru.antontikhonov.android.timetableistu.ui.screen.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.pojo.ThemeEntity

class ThemeAdapter(
    val onThemeClick: (ThemeEntity) -> Unit,
    val onCustomThemeClick: () -> Unit
) : ListAdapter<ThemeEntity, ThemeAdapter.ThemeViewHolder>(ThemeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        return ThemeViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_theme, parent, false))
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ThemeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.themeImage)
        private val header = view.findViewById<TextView>(R.id.themeHeader)

        fun bind(theme: ThemeEntity) {
            header.text = theme.name
            Glide.with(view).load(theme.url).into(imageView)
            if (bindingAdapterPosition == 0) {
                imageView.setOnClickListener {
                    onCustomThemeClick()
                }
            } else {
                imageView.setOnClickListener {
                    onThemeClick(theme)
                }
            }
        }
    }

    object ThemeDiffUtil : DiffUtil.ItemCallback<ThemeEntity>() {

        override fun areItemsTheSame(oldItem: ThemeEntity, newItem: ThemeEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ThemeEntity, newItem: ThemeEntity): Boolean {
            return oldItem == newItem
        }
    }
}
