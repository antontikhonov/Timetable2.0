package site.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import site.antontikhonov.android.timetableistu.pojo.Themes
import site.antontikhonov.android.timetableistu.pojo.TimetableEntity

interface TimetableIstuApi {

    @GET("test.json")
    fun getTimetable(): Single<TimetableEntity>

    @GET("theme/themes.json")
    fun getThemes(): Single<Themes>
}