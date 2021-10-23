package site.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import site.antontikhonov.android.timetableistu.pojo.TimetableEntity

class TimetableRepositoryImpl(private val timetableIstuApi: TimetableIstuApi) : TimetableRepository {

    override fun loadTimetable(): Single<TimetableEntity> {
        return timetableIstuApi.getTimetable()
    }
}