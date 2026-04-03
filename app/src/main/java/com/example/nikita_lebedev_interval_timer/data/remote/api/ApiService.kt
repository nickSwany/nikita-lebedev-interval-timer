package com.example.nikita_lebedev_interval_timer.data.remote.api

import com.example.nikita_lebedev_interval_timer.data.remote.dto.TimerResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/interval-timers/{id}")
    suspend fun getTimerById(@Path("id") id: Int): TimerResponseDTO
}