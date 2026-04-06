package com.example.nikita_lebedev_interval_timer.ui.timer

import com.example.nikita_lebedev_interval_timer.domain.model.Timer

data class TimerScreenUiState(
    val timer: Timer,
    val timerUiState: TimerUiState = TimerUiState.Idle
)