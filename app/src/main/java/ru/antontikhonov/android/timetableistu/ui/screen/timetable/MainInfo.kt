package ru.antontikhonov.android.timetableistu.ui.screen.timetable

data class MainInfo(
    val groupNumber: String,
    val currentDate: String,
    val weekParity: Parity,
)

enum class Parity {
    ODD,
    EVEN
}
