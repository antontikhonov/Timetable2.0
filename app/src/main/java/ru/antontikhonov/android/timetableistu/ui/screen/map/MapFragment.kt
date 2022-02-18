package ru.antontikhonov.android.timetableistu.ui.screen.map

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.databinding.FragmentMapBinding
import ru.antontikhonov.android.timetableistu.ui.screen.showToast


private const val MAP_URL = "https://antontikhonov.ru/timetable/map/map.jpg"

class MapFragment : Fragment(R.layout.fragment_map) {

    private val viewBinding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(MAP_URL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewBinding.progressBar.isVisible = false
                    requireContext().showToast(getString(R.string.error_message))
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewBinding.progressBar.isVisible = false
                    return false
                }
            })
            .into(viewBinding.mapImage)
    }
}
