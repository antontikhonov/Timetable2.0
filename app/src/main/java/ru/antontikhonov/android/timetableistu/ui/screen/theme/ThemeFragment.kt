package ru.antontikhonov.android.timetableistu.ui.screen.theme

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.antontikhonov.android.timetableistu.MainActivity
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.THEME_IMAGE_NAME
import ru.antontikhonov.android.timetableistu.THEME_LOADED_TAG
import ru.antontikhonov.android.timetableistu.architecture.State
import ru.antontikhonov.android.timetableistu.databinding.FragmentThemeBinding
import ru.antontikhonov.android.timetableistu.pojo.ThemeEntity

private const val SPAN_COUNT_THEMES = 3

class ThemeFragment : Fragment(R.layout.fragment_theme) {

    private val viewModel: ThemeViewModel by viewModel()
    private val preferences: SharedPreferences by inject()
    private val adapter = ThemeAdapter(::saveImage)
    private val viewBinding: FragmentThemeBinding by viewBinding(FragmentThemeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.data.observe(viewLifecycleOwner, ::renderUi)
        viewModel.loadThemes()
    }

    private fun initView() {
        with(viewBinding) {
            rvTheme.adapter = adapter
            (rvTheme.layoutManager as? GridLayoutManager)?.spanCount = SPAN_COUNT_THEMES
            rvTheme.addItemDecoration(ThemeGridDecoration(R.dimen.theme_grid_spacing))
        }
    }

    private fun renderUi(state: State<List<ThemeEntity>>) {
        with(viewBinding) {
            progressBar.isVisible = state.loading
            rvTheme.isVisible = !state.loading
            state.content?.let {
                adapter.submitList(it)
            }
        }
    }

    private fun saveImage(theme: ThemeEntity) {
        Glide.with(requireContext()).asBitmap()
            .load(theme.url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    activity?.openFileOutput(THEME_IMAGE_NAME, Context.MODE_PRIVATE).use {
                        resource.compress(Bitmap.CompressFormat.PNG, 100, it)
                    }
                    (activity as? MainActivity)?.restartActivity()
                    preferences.edit()
                        ?.putBoolean(THEME_LOADED_TAG, true)
                        ?.putBoolean("isDarkTheme", theme.isDarkTheme)
                        ?.apply()
                    Toast.makeText(
                        requireContext(),
                        String.format(getString(R.string.theme_changed_toast_message), theme.name),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }
}