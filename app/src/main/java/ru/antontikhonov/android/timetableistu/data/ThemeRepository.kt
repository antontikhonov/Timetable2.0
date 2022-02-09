package ru.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import ru.antontikhonov.android.timetableistu.pojo.Themes

interface ThemeRepository {

    fun loadThemes(): Single<Themes>
}

class ThemeRepositoryImpl(private val timetableApi: TimetableApi) : ThemeRepository {

    override fun loadThemes(): Single<Themes> {
        return timetableApi.getThemes()
    }
}
