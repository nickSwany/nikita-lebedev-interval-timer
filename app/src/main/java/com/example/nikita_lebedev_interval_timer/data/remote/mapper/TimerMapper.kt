package com.example.nikita_lebedev_interval_timer.data.remote.mapper

import com.example.nikita_lebedev_interval_timer.data.remote.dto.IntervalDTO
import com.example.nikita_lebedev_interval_timer.data.remote.dto.TimerDTO
import com.example.nikita_lebedev_interval_timer.domain.model.Interval
import com.example.nikita_lebedev_interval_timer.domain.model.Timer

fun TimerDTO.toDomain(): Timer {
    return Timer(
        timerId = timerId,
        title = title,
        totalTime = totalTime,
        intervals = intervals.map { it.toDomain() }
    )
}

fun IntervalDTO.toDomain(): Interval {
    return Interval(
        title = title,
        time = time
    )
}