package ru.antontikhonov.android.timetableistu.ui.screen.timetable

import android.os.Bundle
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.databinding.FragmentDayBinding
import ru.antontikhonov.android.timetableistu.recycler.ItemDecorator
import ru.antontikhonov.android.timetableistu.recycler.TimetableAdapter

private const val DAY_NUMBER = "day_number"

class DayFragment : Fragment(R.layout.fragment_day) {

    private val viewModel: TimetableViewModel by viewModel()
    private val adapter = TimetableAdapter()
    private val viewBinding: FragmentDayBinding by viewBinding(FragmentDayBinding::bind)

    @DimenRes
    private val startMargin = R.dimen.timetable_list_divider_margin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.recyclerView) {
            adapter = this@DayFragment.adapter
            addItemDecoration(ItemDecorator(resources.getDimensionPixelSize(startMargin)))
        }
        val number = arguments?.getInt(DAY_NUMBER)!!
        viewModel.data.observe(viewLifecycleOwner) {
            with(viewBinding) {
                classNotFound.isVisible = it[number].isEmpty()
                viewBinding.progressBar.isVisible = false
                viewBinding.recyclerView.isVisible = it[number].isNotEmpty()
            }
            adapter.submitList(it[number])
        }
        viewModel.loadTimetable()
    }

    companion object {

        fun newInstance(dayNumber: Int): DayFragment {
            return DayFragment().also { fragment ->
                fragment.arguments = Bundle().also {
                    it.putInt(DAY_NUMBER, dayNumber)
                }
            }
        }
    }
}
