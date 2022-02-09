package ru.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import ru.antontikhonov.android.timetableistu.pojo.TimetableEntity

interface TimetableRepository {

    fun loadTimetable(): Single<TimetableEntity>
}

class TimetableRepositoryImpl(private val timetableApi: TimetableApi) : TimetableRepository {

    override fun loadTimetable(): Single<TimetableEntity> {
        return timetableApi.getTimetable()
    }
}
