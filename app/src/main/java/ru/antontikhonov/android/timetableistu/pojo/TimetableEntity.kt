package ru.antontikhonov.android.timetableistu.pojo

import com.google.gson.annotations.SerializedName

data class TimetableEntity(
    @SerializedName("number_of_group")
    val numberOfGroup: String,
    val days: List<DayEntity>
)
