package site.antontikhonov.android.timetableistu.pojo

import com.google.gson.annotations.SerializedName

data class News(

    @SerializedName("news")
    val news: List<NewsEntity>
)

data class NewsEntity(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("url")
    val url: String?
)