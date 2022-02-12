package ru.antontikhonov.android.timetableistu

import androidx.navigation.NavController
import ru.antontikhonov.android.timetableistu.ui.screen.chat.ChatFragmentDirections
import ru.antontikhonov.android.timetableistu.ui.screen.groups.GroupsFragmentDirections
import ru.antontikhonov.android.timetableistu.ui.screen.news.NewsFragmentDirections
import ru.antontikhonov.android.timetableistu.ui.screen.settings.SettingsFragmentDirections
import ru.antontikhonov.android.timetableistu.ui.screen.timetable.TimetableFragmentDirections

class FragmentNavigator(private val navController: NavController) {

    fun navigateByItemId(itemId: Int) {
        when (itemId) {
            R.id.timetable -> navigateToTimetable()
            R.id.chat -> navigateToChat()
            R.id.news -> navigateToNews()
            R.id.settings -> navigateToSettings()
            GROUPS_NAVIGATION_ID -> navigateToGroupNumber()
            else -> throw NoSuchElementException(EXCEPTION_MESSAGE)
        }
    }

    private fun navigateToTimetable() =
        navController.navigate(TimetableFragmentDirections.actionGlobalTimetableFragment())

    private fun navigateToChat() =
        navController.navigate(ChatFragmentDirections.actionGlobalChatFragment())

    private fun navigateToNews() =
        navController.navigate(NewsFragmentDirections.actionGlobalNewsFragment())

    private fun navigateToSettings() =
        navController.navigate(SettingsFragmentDirections.actionGlobalSettingsFragment())

    private fun navigateToGroupNumber() =
        navController.navigate(GroupsFragmentDirections.actionGlobalGroupsFragment())
}
