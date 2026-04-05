package com.example.nikita_lebedev_interval_timer.ui.loading

import com.example.nikita_lebedev_interval_timer.domain.model.Timer

sealed class LoadingEvent {
    data class NavigateToTimer(val timer: Timer) : LoadingEvent()
}