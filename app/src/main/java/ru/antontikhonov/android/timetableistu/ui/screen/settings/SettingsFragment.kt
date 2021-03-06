package ru.antontikhonov.android.timetableistu.ui.screen.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewBinding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            groupChoiceButton.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_groupsFragment)
            }
            themeChoiceButton.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_themeFragment)
            }
        }
    }
}
