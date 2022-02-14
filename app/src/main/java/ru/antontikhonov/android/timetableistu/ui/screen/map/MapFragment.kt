package ru.antontikhonov.android.timetableistu.ui.screen.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.antontikhonov.android.timetableistu.R
import ru.antontikhonov.android.timetableistu.databinding.FragmentMapBinding


private const val MAP_URL = "https://antontikhonov.ru/timetable/map/map.jpg"

class MapFragment : Fragment(R.layout.fragment_map) {

    private val viewBinding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    var options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.progress_animation)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(MAP_URL)
            .apply(options)
            .into(viewBinding.mapImage)
    }
}
