package ru.antontikhonov.android.timetableistu.ui.screen.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.pojo.NewsEntity

class NewsAdapter : ListAdapter<NewsEntity, NewsAdapter.NewsViewHolder>(NewsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.newsImage)
        private val newsText = view.findViewById<TextView>(R.id.newsText)
        private val newsDate = view.findViewById<TextView>(R.id.newsDate)

        fun bind(news: NewsEntity) {
            newsText.text = news.text
            news.url?.let {
                imageView.isVisible = true
                Glide.with(view).load(news.url).into(imageView)
            } ?: run {
                imageView.isVisible = false
            }
            newsDate.text = news.date
        }
    }

    object NewsDiffUtil : DiffUtil.ItemCallback<NewsEntity>() {

        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }
    }
}
