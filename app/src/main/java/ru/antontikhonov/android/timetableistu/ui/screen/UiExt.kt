package ru.antontikhonov.android.timetableistu.ui.screen

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: CharSequence?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
