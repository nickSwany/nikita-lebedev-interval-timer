package com.example.nikita_lebedev_interval_timer.domain.model

data class Timer(
    val timerId: Int,
    val title: String,
    val totalTime: Long,
    val intervals: List<Interval>
)