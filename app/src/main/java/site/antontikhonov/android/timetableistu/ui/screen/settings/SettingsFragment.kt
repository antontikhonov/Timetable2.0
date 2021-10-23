package site.antontikhonov.android.timetableistu.ui.screen.settings

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import site.antontikhonov.android.timetableistu.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.themeChoiceButton).setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_themeFragment)
        }
    }
}