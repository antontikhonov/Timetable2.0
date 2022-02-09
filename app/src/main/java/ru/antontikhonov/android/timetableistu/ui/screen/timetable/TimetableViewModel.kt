package ru.antontikhonov.android.timetableistu.ui.screen.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.antontikhonov.android.timetableistu.data.TimetableRepository
import ru.antontikhonov.android.timetableistu.pojo.PairClassEntity
import java.util.*

private const val TWO_WEEK_IN_MILLISECONDS = 60 * 60 * 24 * 14 * 1000

class TimetableViewModel(private val timetableRepository: TimetableRepository) : ViewModel() {

    val data: LiveData<List<List<PairClassEntity>>>
        get() = mutableData
    val number: LiveData<String>
        get() = mutableNumber
    val parity: LiveData<Parity>
        get() = mutableParity
    private val mutableData = MutableLiveData<List<List<PairClassEntity>>>()
    private val mutableNumber = MutableLiveData<String>()
    private val mutableParity = MutableLiveData<Parity>()

    fun loadGroupNumber() {
        timetableRepository.loadTimetable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mutableNumber.value = it.numberOfGroup
                    mutableParity.value = getParity()
                },
                onError = {
                    //todo прописать обработку ошибок
                }
            )

    }

    fun loadTimetable() {
        timetableRepository.loadTimetable()
            .subscribeOn(Schedulers.io())
            .map { result ->
                mutableNumber.postValue(result.numberOfGroup)
                result.days.map {
                    it.classes.filter { pairClass ->
                        !(pairClass.odd == null && pairClass.even == null)
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mutableData.value = it
                },
                onError = {
                    //todo прописать обработку ошибок
                }
            )
    }

    private fun getParity(): Parity {
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.set(2021, Calendar.AUGUST, 30, 0, 0, 0)
        return if ((currentDate.time - calendar.timeInMillis) % TWO_WEEK_IN_MILLISECONDS < (TWO_WEEK_IN_MILLISECONDS / 2)) {
            Parity.ODD
        } else {
            Parity.EVEN
        }
    }

    enum class Parity {
        ODD,
        EVEN
    }
}
