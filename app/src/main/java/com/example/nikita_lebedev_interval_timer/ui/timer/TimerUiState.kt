package com.example.nikita_lebedev_interval_timer.ui.timer

sealed class TimerUiState {
    object Idle : TimerUiState()
    data class Running(
        val activeIntervalIndex: Int,
        val timeToEndInterval: Long,
        val spentTime: Long
    ) : TimerUiState()

    data class Paused(
        val activeIntervalIndex: Int,
        val timeToEndInterval: Long,
        val spentTime: Long
    ) : TimerUiState()

    object Completed : TimerUiState()

    data class DownTimer(val timer: String) : TimerUiState()
}