package ru.antontikhonov.android.timetableistu.ui.screen.timetable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment(R.layout.fragment_timetable) {

    private val viewModel: TimetableViewModel by viewModel()
    private val viewBinding: FragmentTimetableBinding by viewBinding(FragmentTimetableBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewPager.adapter = DayPagerAdapter(this@TimetableFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.monday)
                    1 -> tab.text = getString(R.string.tuesday)
                    2 -> tab.text = getString(R.string.wednesday)
                    3 -> tab.text = getString(R.string.thursday)
                    4 -> tab.text = getString(R.string.friday)
                    5 -> tab.text = getString(R.string.saturday)
                }
            }.attach()
        }

        viewModel.mainInfo.observe(viewLifecycleOwner) { state ->
            state.content?.let {
                with(viewBinding) {
                    timetableHeader.text = it.groupNumber
                    date.text = it.currentDate
                    weekParity.text = if (it.weekParity == Parity.ODD) {
                        getString(R.string.odd_week)
                    } else {
                        getString(R.string.even_week)
                    }
                }
            }
        }
        viewModel.loadMainInfo()
    }
}
