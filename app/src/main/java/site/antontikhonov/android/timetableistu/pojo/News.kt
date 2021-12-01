package site.antontikhonov.android.timetableistu.pojo

data class News(
    val news: List<NewsEntity>
)

data class NewsEntity(
    val id: String,
    val date: String,
    val text: String,
    val url: String?
)