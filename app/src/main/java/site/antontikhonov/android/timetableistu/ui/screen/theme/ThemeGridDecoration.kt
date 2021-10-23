package site.antontikhonov.android.timetableistu.ui.screen.theme

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ThemeGridDecoration(@DimenRes private val spacingDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        val spacingPx = view.resources.getDimensionPixelSize(spacingDp)
        rect.left = spacingPx / 2
        rect.right = spacingPx / 2
    }
}