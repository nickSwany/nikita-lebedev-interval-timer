package com.example.nikita_lebedev_interval_timer.ui.loading

data class LoadingUiState(
    val timerId: String = "68",
    val isLoading: Boolean = false,
    val error: String? = null
)