package ru.antontikhonov.android.timetableistu.pojo

import com.google.gson.annotations.SerializedName

data class PairClassEntity(

    @SerializedName("time")
    val time: String,

    @SerializedName("odd")
    val odd: String?,

    @SerializedName("even")
    val even: String?,
)
