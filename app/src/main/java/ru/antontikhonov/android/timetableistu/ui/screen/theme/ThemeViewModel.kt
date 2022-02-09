package ru.antontikhonov.android.timetableistu.ui.screen.theme

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
import ru.antontikhonov.android.timetableistu.data.ThemeRepository
import ru.antontikhonov.android.timetableistu.pojo.ThemeEntity

class ThemeViewModel(private val themeRepository: ThemeRepository) : ViewModel() {

    val data: LiveData<State<List<ThemeEntity>>>
        get() = mutableData
    private val mutableData = MutableLiveData<State<List<ThemeEntity>>>()

    fun loadThemes() {
        mutableData.value = stateLoading()
        themeRepository.loadThemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mutableData.value = stateContent(it.themes)
                },
                onError = {
                    mutableData.value = stateError(it)
                }
            )
    }
}
