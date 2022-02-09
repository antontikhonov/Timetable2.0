package site.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import site.antontikhonov.android.timetableistu.pojo.News
import site.antontikhonov.android.timetableistu.pojo.Themes
import site.antontikhonov.android.timetableistu.pojo.TimetableEntity

interface TimetableApi {

    @GET("test.json")
    fun getTimetable(): Single<TimetableEntity>

    @GET("theme/themes.json")
    fun getThemes(): Single<Themes>

    @GET("news/news.json")
    fun getNews(): Single<News>
}