package ru.antontikhonov.android.timetableistu.pojo

import com.google.gson.annotations.SerializedName

data class Groups(

    @SerializedName("groups")
    val groups: List<String>
)
