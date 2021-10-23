package site.antontikhonov.android.timetableistu.ui.screen.timetable

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import site.antontikhonov.android.timetableistu.R
import site.antontikhonov.android.timetableistu.recycler.ItemDecorator
import site.antontikhonov.android.timetableistu.recycler.TimetableAdapter

private const val DAY_NUMBER = "day_number"

class DayFragment : Fragment(R.layout.fragment_day) {

    private val viewModel by viewModel<TimetableViewModel>()
    private val adapter by lazy { TimetableAdapter() }

    @DimenRes
    private val startMargin = R.dimen.timetable_list_divider_margin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val textView = view.findViewById<TextView>(R.id.class_not_found)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        with(recyclerView) {
            adapter = this@DayFragment.adapter
            addItemDecoration(ItemDecorator(getDimenInPixels(view, startMargin)))
        }
        val number = arguments?.getInt(DAY_NUMBER)!!
        viewModel.data.observe(viewLifecycleOwner, {
            textView.isVisible = it[number].isEmpty()
            progressBar.isVisible = false
            recyclerView.isVisible = it[number].isNotEmpty()
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