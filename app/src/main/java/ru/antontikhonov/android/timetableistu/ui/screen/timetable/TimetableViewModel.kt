package ru.antontikhonov.android.timetableistu.ui.screen.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.zipWith
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.antontikhonov.android.timetableistu.architecture.State
import ru.antontikhonov.android.timetableistu.architecture.stateError
import ru.antontikhonov.android.timetableistu.architecture.stateLoading
import ru.antontikhonov.android.timetableistu.data.StartDateRepository
import ru.antontikhonov.android.timetableistu.data.TimetableRepository
import ru.antontikhonov.android.timetableistu.pojo.PairClassEntity
import java.text.SimpleDateFormat
import java.util.*

private const val TWO_WEEK_IN_MILLISECONDS = 60 * 60 * 24 * 14 * 1000
private const val DATE_PATTERN = "EEE, dd.MM.yyyy"

class TimetableViewModel(
    private val timetableRepository: TimetableRepository,
    private val startDateRepository: StartDateRepository,
) : ViewModel() {

    val data: LiveData<State<List<List<PairClassEntity>>>>
        get() = mutableData
    val mainInfo: LiveData<State<MainInfo>>
        get() = mutableMainInfo
    private val mutableData = MutableLiveData<State<List<List<PairClassEntity>>>>()
    private val mutableMainInfo = MutableLiveData<State<MainInfo>>()

    fun loadTimetable() {
        mutableData.value = stateLoading()
        timetableRepository.loadTimetable()
            .subscribeOn(Schedulers.io())
            .map { result ->
                result.days.map {
                    it.classes.filter { pairClass ->
                        !(pairClass.odd == null && pairClass.even == null)
                    }
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mutableData.value = State(content = it)
                },
                onError = {
                    mutableData.value = State(error = it)
                }
            )
    }

    fun loadMainInfo() {
        timetableRepository.loadTimetable()
            .zipWith(startDateRepository.loadStartDate())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { (timetableEntity, startDate) ->
                    val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
                    mutableMainInfo.value = State(
                        content = MainInfo(
                            groupNumber = timetableEntity.numberOfGroup.uppercase(Locale.getDefault()),
                            currentDate = formatter.format(Date()),
                            weekParity = getParity(startDate.startDate),
                        )
                    )
                },
                onError = {
                    mutableMainInfo.value = stateError(it)
                }
            )
    }

    private fun getParity(date: Date): Parity {
        return if ((Date().time - date.time) % TWO_WEEK_IN_MILLISECONDS < (TWO_WEEK_IN_MILLISECONDS / 2)) {
            Parity.ODD
        } else {
            Parity.EVEN
        }
    }
}
