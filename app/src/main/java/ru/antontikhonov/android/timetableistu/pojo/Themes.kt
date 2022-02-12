package ru.antontikhonov.android.timetableistu.pojo

import com.google.gson.annotations.SerializedName

data class Themes(

    @SerializedName("themes")
    val themes: List<ThemeEntity>,
)

data class ThemeEntity(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("isDarkTheme")
    val isDarkTheme: Boolean = false,

    @SerializedName("url")
    val url: String,
)
