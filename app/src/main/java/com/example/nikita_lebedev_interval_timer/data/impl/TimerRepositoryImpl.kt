package com.example.nikita_lebedev_interval_timer.data.impl

import com.example.nikita_lebedev_interval_timer.data.remote.api.ApiService
import com.example.nikita_lebedev_interval_timer.data.remote.mapper.toDomain
import com.example.nikita_lebedev_interval_timer.domain.model.Timer
import com.example.nikita_lebedev_interval_timer.domain.repository.TimerRepository
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TimerRepository {

    override suspend fun getTimerById(id: Int): Result<Timer> {
        return try {
            val response = apiService.getTimerById(id)
            Result.success(response.timer.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}