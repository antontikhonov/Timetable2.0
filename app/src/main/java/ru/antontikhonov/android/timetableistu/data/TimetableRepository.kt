package ru.antontikhonov.android.timetableistu.data

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Single
import ru.antontikhonov.android.timetableistu.GROUP_NUMBER_TAG
import ru.antontikhonov.android.timetableistu.pojo.TimetableEntity

private const val DEFAULT_GROUP = "Б18-831-1"

interface TimetableRepository {

    fun loadTimetable(): Single<TimetableEntity>
}

class TimetableRepositoryImpl(
    private val timetableApi: TimetableApi,
    private val preferences: SharedPreferences
) : TimetableRepository {

    override fun loadTimetable(): Single<TimetableEntity> {
        return timetableApi.getTimetable(
            preferences.getString(GROUP_NUMBER_TAG, DEFAULT_GROUP) ?: DEFAULT_GROUP
        )
    }
}
