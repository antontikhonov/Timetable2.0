package ru.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import ru.antontikhonov.android.timetableistu.pojo.News

interface NewsRepository {

    fun loadNews(): Single<News>
}

class NewsRepositoryImpl(private val timetableApi: TimetableApi) : NewsRepository {

    override fun loadNews(): Single<News> {
        return timetableApi.getNews()
    }
}
