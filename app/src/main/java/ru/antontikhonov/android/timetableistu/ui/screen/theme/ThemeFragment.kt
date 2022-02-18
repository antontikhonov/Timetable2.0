package ru.antontikhonov.android.timetableistu.ui.screen.theme

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
import ru.antontikhonov.android.timetableistu.ui.screen.showToast


private const val SPAN_COUNT_THEMES = 3

class ThemeFragment : Fragment(R.layout.fragment_theme) {

    private val viewModel: ThemeViewModel by viewModel()
    private val preferences: SharedPreferences by inject()
    private val adapter = ThemeAdapter(::saveImage, ::loadCustomImage)
    private val viewBinding: FragmentThemeBinding by viewBinding(FragmentThemeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setOnBackPressedListener()
        viewModel.data.observe(viewLifecycleOwner, ::renderUi)
        viewModel.loadThemes()
    }

    private fun initView() {
        with(viewBinding) {
            recyclerView.adapter = adapter
            (recyclerView.layoutManager as? GridLayoutManager)?.spanCount = SPAN_COUNT_THEMES
            recyclerView.addItemDecoration(ThemeGridDecoration(R.dimen.theme_grid_spacing))
        }
    }

    private fun renderUi(state: State<List<ThemeEntity>>) {
        with(viewBinding) {
            progressBar.isVisible = state.loading
            recyclerView.isVisible = !state.loading
            state.content?.let {
                adapter.submitList(it)
            }
            state.error?.let {
                requireContext().showToast(getString(R.string.error_message))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            val selectedImage: Uri = data.data!!
            val stream = requireActivity().contentResolver.openInputStream(selectedImage)
            val bitmap = BitmapFactory.decodeStream(stream)
            bitmap.setCustomBackground(null)
        }
    }

    private fun loadCustomImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 0)
    }

    private fun saveImage(theme: ThemeEntity) {
        viewBinding.progressBar.isVisible = true
        Glide.with(requireContext()).asBitmap()
            .load(theme.url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    resource.setCustomBackground(theme)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

    private fun Bitmap.setCustomBackground(theme: ThemeEntity?) {
        activity?.openFileOutput(THEME_IMAGE_NAME, Context.MODE_PRIVATE).use {
            compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        (activity as? MainActivity)?.updateUi()
        viewBinding.progressBar.isVisible = false
        preferences.edit()
            ?.putBoolean(THEME_LOADED_TAG, true)
            ?.putBoolean("isDarkTheme", theme?.isDarkTheme ?: false)
            ?.apply()
        requireContext().showToast(
            String.format(getString(R.string.theme_changed_toast_message), theme?.name ?: "my own")
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
