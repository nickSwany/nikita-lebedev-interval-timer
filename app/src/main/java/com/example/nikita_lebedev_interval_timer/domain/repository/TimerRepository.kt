package com.example.nikita_lebedev_interval_timer.domain.repository

import com.example.nikita_lebedev_interval_timer.domain.model.Timer

interface TimerRepository {
    suspend fun getTimerById(id: Int): Result<Timer>
}