package ru.antontikhonov.android.timetableistu.pojo

import com.google.gson.annotations.SerializedName

data class DayEntity(

    @SerializedName("day")
    val day: String,

    @SerializedName("classes")
    val classes: List<PairClassEntity>,
)
