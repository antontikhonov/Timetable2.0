package site.antontikhonov.android.timetableistu.ui.screen.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import site.antontikhonov.android.timetableistu.architecture.State
import site.antontikhonov.android.timetableistu.architecture.stateError
import site.antontikhonov.android.timetableistu.architecture.stateLoading
import site.antontikhonov.android.timetableistu.data.NewsRepository
import site.antontikhonov.android.timetableistu.pojo.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val data: LiveData<State<List<NewsEntity>>>
        get() = mutableData
    private val mutableData = MutableLiveData<State<List<NewsEntity>>>()

    fun loadNews() {
        mutableData.value = stateLoading()
        newsRepository.loadNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mutableData.value = State(it.news)
                },
                onError = {
                    mutableData.value = stateError(it)
                }
            )
    }
}