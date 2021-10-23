package site.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import site.antontikhonov.android.timetableistu.pojo.TimetableEntity

interface TimetableRepository {

    fun loadTimetable(): Single<TimetableEntity>
}