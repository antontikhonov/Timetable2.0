package site.antontikhonov.android.timetableistu.ui.screen.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import site.antontikhonov.android.timetableistu.data.ThemeRepository
import site.antontikhonov.android.timetableistu.pojo.ThemeEntity

class ThemeViewModel(private val themeRepository: ThemeRepository) : ViewModel() {

    val data: LiveData<List<ThemeEntity>>
        get() = mutableData
    private val mutableData = MutableLiveData<List<ThemeEntity>>()

    fun loadGroupNumber() {
        themeRepository.loadThemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mutableData.value = it.themes
                },
                onError = {

                }
            )
    }
}