package ru.antontikhonov.android.timetableistu.ui.screen.news

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class NewsLinearDecoration(@DimenRes private val spacingDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        val spacingPx = view.resources.getDimensionPixelSize(spacingDp)
        rect.bottom = spacingPx / 2
    }
}
