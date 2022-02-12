package ru.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import ru.antontikhonov.android.timetableistu.pojo.StartDate

interface StartDateRepository {

    fun loadStartDate(): Single<StartDate>
}

class StartDateRepositoryImpl(private val timetableApi: TimetableApi) : StartDateRepository {

    override fun loadStartDate(): Single<StartDate> {
        return timetableApi.getStartDate()
    }
}
