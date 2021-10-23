package site.antontikhonov.android.timetableistu.ui.screen.timetable

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val DAY_COUNTS = 6

class DayPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = DAY_COUNTS

    override fun createFragment(position: Int): Fragment = DayFragment.newInstance(position)
}