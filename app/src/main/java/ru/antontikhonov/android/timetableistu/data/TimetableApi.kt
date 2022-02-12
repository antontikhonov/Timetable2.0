package ru.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.antontikhonov.android.timetableistu.pojo.*

interface TimetableApi {

    @GET("timetable/{id}")
    fun getTimetable(
        @Path("id") id: String
    ): Single<TimetableEntity>

    @GET("theme/themes.json")
    fun getThemes(): Single<Themes>

    @GET("news/news.json")
    fun getNews(): Single<News>

    @GET("groups")
    fun getGroups(): Single<Groups>

    @GET("start-date")
    fun getStartDate(): Single<StartDate>
}
