package ru.antontikhonov.android.timetableistu.ui.screen.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.antontikhonov.android.timetableistu.architecture.State
import ru.antontikhonov.android.timetableistu.architecture.stateContent
import ru.antontikhonov.android.timetableistu.architecture.stateError
import ru.antontikhonov.android.timetableistu.architecture.stateLoading
import ru.antontikhonov.android.timetableistu.data.GroupsRepository
import java.util.*

class GroupsViewModel(private val groupsRepository: GroupsRepository) : ViewModel() {

    val data: LiveData<State<List<String>>>
        get() = mutableData
    private val mutableData = MutableLiveData<State<List<String>>>()

    fun loadNews() {
        mutableData.value = stateLoading()
        groupsRepository.loadGroups()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.groups.map { group ->
                    group.uppercase(Locale.getDefault())
                }
            }
            .subscribeBy(
                onSuccess = {
                    mutableData.value = stateContent(it)
                },
                onError = {
                    mutableData.value = stateError(it)
                }
            )
    }

    fun findGroup(query: String): List<String> {
        return mutableData.value?.content?.filter {
            it.startsWith(query.uppercase(Locale.getDefault()))
        } ?: emptyList()
    }
}
