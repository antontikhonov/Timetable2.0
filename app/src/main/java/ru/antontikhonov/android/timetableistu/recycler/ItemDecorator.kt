package ru.antontikhonov.android.timetableistu.recycler

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(@Px private val bottomOffsetPx: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, s: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != RecyclerView.NO_POSITION) {
            rect.set(0, 0, 0, bottomOffsetPx)
        }
    }
}
