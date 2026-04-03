package com.example.nikita_lebedev_interval_timer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TimerDTO(
    @SerializedName("timer_id")
    val timerId: Int,
    val title: String,
    @SerializedName("total_time")
    val totalTime: Long,
    val intervals: List<IntervalDTO>
)