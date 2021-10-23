package site.antontikhonov.android.timetableistu.ui.screen.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import org.koin.androidx.viewmodel.ext.android.viewModel
import site.antontikhonov.android.timetableistu.MainActivity
import site.antontikhonov.android.timetableistu.R
import site.antontikhonov.android.timetableistu.THEME_IMAGE_NAME
import site.antontikhonov.android.timetableistu.THEME_LOADED_TAG
import java.lang.Exception

class ThemeFragment : Fragment(R.layout.fragment_theme) {

    private val viewModel by viewModel<ThemeViewModel>()
    private lateinit var recyclerView: RecyclerView
    private val adapter = ThemeAdapter(::saveImage)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        viewModel.data.observe(viewLifecycleOwner, { adapter.submitList(it) })
        viewModel.loadGroupNumber()
    }

    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.rvTheme)
        recyclerView.adapter = adapter
        (recyclerView.layoutManager as? GridLayoutManager)?.spanCount = 3
    }

    private fun saveImage(url: String, name: String) {
        Glide.with(requireContext()).asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    try {
                        val fos = activity?.openFileOutput(THEME_IMAGE_NAME, Context.MODE_PRIVATE)
                        resource.compress(Bitmap.CompressFormat.PNG, 100, fos)
                        fos?.flush()
                        fos?.close()
                        (activity as? MainActivity)?.updateBackground()
                        val preferences = activity?.getSharedPreferences("theme",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        preferences?.edit()
                            ?.putBoolean(THEME_LOADED_TAG, true)
                            ?.apply()
                        Toast.makeText(
                            requireContext(),
                            String.format(getString(R.string.theme_changed_toast_message), name),
                            Toast.LENGTH_LONG)
                            .show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }
}