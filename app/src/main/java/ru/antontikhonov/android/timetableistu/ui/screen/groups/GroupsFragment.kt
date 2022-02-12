package ru.antontikhonov.android.timetableistu.ui.screen.groups

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.antontikhonov.android.timetableistu.GROUP_NUMBER_TAG
import ru.antontikhonov.android.timetableistu.MainActivity
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.architecture.State
import ru.antontikhonov.android.timetableistu.databinding.FragmentGroupsBinding
import ru.antontikhonov.android.timetableistu.ui.screen.news.NewsLinearDecoration
import ru.antontikhonov.android.timetableistu.ui.screen.showToast

class GroupsFragment : Fragment(R.layout.fragment_groups) {

    private val viewBinding: FragmentGroupsBinding by viewBinding(FragmentGroupsBinding::bind)
    private val viewModel: GroupsViewModel by viewModel()
    private val preferences: SharedPreferences by inject()
    private val adapter = GroupsAdapter(::changeGroup)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setOnBackPressedListener()
        viewModel.data.observe(viewLifecycleOwner, ::renderUi)
        viewModel.loadNews()
    }

    private fun initView() {
        with(viewBinding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(NewsLinearDecoration(R.dimen.group_spacing))

            searchView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun afterTextChanged(s: Editable?) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.submitList(viewModel.findGroup(s.toString()))
                }
            })
        }
    }

    private fun renderUi(state: State<List<String>>) {
        with(viewBinding) {
            progressBar.isVisible = state.loading
            recyclerView.isVisible = !state.loading
            state.content?.let {
                adapter.submitList(it)
                searchView.isVisible = true
            }
            state.error?.let {
                requireContext().showToast(getString(R.string.error_message))
            }
        }
    }

    private fun changeGroup(group: String) {
        (activity as? MainActivity)?.restartActivity()
        preferences.edit()
            ?.putString(GROUP_NUMBER_TAG, group)
            ?.apply()
        requireContext().showToast(
            String.format(getString(R.string.group_changed_toast_message), group)
        )
    }

    private fun setOnBackPressedListener() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
    }
}
