package site.antontikhonov.android.timetableistu.ui.screen.timetable

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import site.antontikhonov.android.timetableistu.R
import java.text.SimpleDateFormat
import java.util.*

open class TimetableFragment : Fragment(R.layout.fragment_timetable) {

    private val viewModel by viewModel<TimetableViewModel>()
    private lateinit var timetableHeader: TextView
    private lateinit var weekParity: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val formatter = SimpleDateFormat("EEE, dd.MM.yyyy", Locale.getDefault())
        view.findViewById<TextView>(R.id.date).also {
            it.text = formatter.format(Date())
        }
        viewPager.adapter = DayPagerAdapter(this)
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

        timetableHeader = view.findViewById(R.id.timetableHeader)
        weekParity = view.findViewById(R.id.weekParity)

        viewModel.number.observe(viewLifecycleOwner, {
            timetableHeader.text = it
        })
        viewModel.parity.observe(viewLifecycleOwner, {
            if (it == TimetableViewModel.Parity.ODD) {
                weekParity.text = requireContext().getString(R.string.odd_week)
            } else {
                weekParity.text = requireContext().getString(R.string.even_week)
            }
        })
        viewModel.loadGroupNumber()
    }
}