package ru.antontikhonov.android.timetableistu.pojo

data class Themes(
    val themes: List<ThemeEntity>
)

data class ThemeEntity(
    val id: String,
    val name: String,
    val isDarkTheme: Boolean,
    val url: String
)
