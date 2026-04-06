package com.example.nikita_lebedev_interval_timer.ui.timer


fun formateTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%d:%02d".format(minutes, remainingSeconds)
}

fun getIntervalItemState(index: Int, timerState: TimerUiState): IntervalItemState {
    return when (timerState) {
        TimerUiState.Completed -> IntervalItemState.Completed

        TimerUiState.Idle -> {
            if (index == 0) {
                IntervalItemState.Active
            } else {
                IntervalItemState.Idle
            }
        }

        is TimerUiState.Paused -> {
            when {
                index == timerState.activeIntervalIndex -> IntervalItemState.ActivePaused
                index < timerState.activeIntervalIndex -> IntervalItemState.Completed
                else -> IntervalItemState.Idle
            }
        }

        is TimerUiState.Running -> {
            when {
                index == timerState.activeIntervalIndex -> IntervalItemState.Active
                index < timerState.activeIntervalIndex -> IntervalItemState.Completed
                else -> IntervalItemState.Idle
            }
        }
    }
}

fun getIntervalProgress(index: Int, timerState: TimerUiState, intervalDuration: Long): Float {
    return when (timerState) {
        is TimerUiState.Paused, is TimerUiState.Running -> {
            val activeIndex = when (timerState) {
                is TimerUiState.Paused -> timerState.activeIntervalIndex
                is TimerUiState.Running -> timerState.activeIntervalIndex
                else -> -1
            }

            val remaining = when (timerState) {
                is TimerUiState.Paused -> timerState.timeToEndInterval
                is TimerUiState.Running -> timerState.timeToEndInterval
                else -> 0L
            }

            if (index == activeIndex) {
                1f - (remaining.toFloat() / intervalDuration.toFloat())
            } else 0f
        }

        else -> 0f
    }
}