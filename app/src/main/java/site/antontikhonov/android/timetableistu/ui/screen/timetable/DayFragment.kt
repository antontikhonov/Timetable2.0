package site.antontikhonov.android.timetableistu.ui.screen.timetable

import android.os.Bundle
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import site.antontikhonov.android.timetableistu.R
import site.antontikhonov.android.timetableistu.databinding.FragmentDayBinding
import site.antontikhonov.android.timetableistu.recycler.ItemDecorator
import site.antontikhonov.android.timetableistu.recycler.TimetableAdapter

private const val DAY_NUMBER = "day_number"

class DayFragment : Fragment(R.layout.fragment_day) {

    private val viewModel by viewModel<TimetableViewModel>()
    private val adapter by lazy { TimetableAdapter() }
    private val viewBinding: FragmentDayBinding by viewBinding(FragmentDayBinding::bind)

    @DimenRes
    private val startMargin = R.dimen.timetable_list_divider_margin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.recyclerView) {
            adapter = this@DayFragment.adapter
            addItemDecoration(ItemDecorator(getDimenInPixels(view, startMargin)))
        }
        val number = arguments?.getInt(DAY_NUMBER)!!
        viewModel.data.observe(viewLifecycleOwner, {
            with(viewBinding) {
                classNotFound.isVisible = it[number].isEmpty()
                viewBinding.progressBar.isVisible = false
                viewBinding.recyclerView.isVisible = it[number].isNotEmpty()
            }
            adapter.submitList(it[number])
        })
        viewModel.loadTimetable()
    }

    private fun getDimenInPixels(view: View, marginRes: Int) =
        view.context.resources.getDimensionPixelSize(marginRes)

    companion object {

        fun newInstance(dayNumber: Int): DayFragment =
            DayFragment().also { fragment ->
                fragment.arguments = Bundle().also {
                    it.putInt(DAY_NUMBER, dayNumber)
                }
            }
    }
}