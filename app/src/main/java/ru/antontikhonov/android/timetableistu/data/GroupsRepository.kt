package ru.antontikhonov.android.timetableistu.data

import io.reactivex.rxjava3.core.Single
import ru.antontikhonov.android.timetableistu.pojo.Groups

interface GroupsRepository {

    fun loadGroups(): Single<Groups>
}

class GroupsRepositoryImpl(private val timetableApi: TimetableApi) : GroupsRepository {

    override fun loadGroups(): Single<Groups> {
        return timetableApi.getGroups()
    }
}
