package com.example.nikita_lebedev_interval_timer.ui.timer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikita_lebedev_interval_timer.domain.model.Timer
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val timerSoundsPlayer: TimerSoundsPlayer
) : ViewModel() {

    private val timer: Timer = savedStateHandle.get<String>("timerJson")?.let {
        Gson().fromJson(
            it,
            Timer::class.java
        )
    } ?: throw IllegalArgumentException("Timer not found")

    private val _uiState = MutableStateFlow<TimerUiState>(TimerUiState.Idle)
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()
    val timerData: Timer = timer
    private var timerJob: Job? = null
    private var startTimer: Long = 0L
    private var pausedTimer: Long = 0L
    private var lastIntervalIndex: Int = -1
    private var isSoundPlayed: Boolean = false

    fun start() {
        startTimer = System.currentTimeMillis() - pausedTimer * 1000
        if (!isSoundPlayed) {
            timerSoundsPlayer.playOneTime()
            isSoundPlayed = true
        }
        startTicking()
    }

    fun pause() {
        timerJob?.cancel()
        val state = _uiState.value
        if (state is TimerUiState.Running) {
            pausedTimer = state.spentTime
            _uiState.value = TimerUiState.Paused(
                activeIntervalIndex = state.activeIntervalIndex,
                timeToEndInterval = state.timeToEndInterval,
                spentTime = state.spentTime
            )
        }
    }

    fun resume() {
        start()
    }

    fun reset() {
        timerJob?.cancel()
        pausedTimer = 0L
        lastIntervalIndex = -1
        isSoundPlayed = false
        _uiState.value = TimerUiState.Idle
    }

    fun restart() {
        reset()
        start()
    }

    private fun startTicking() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(100L)
                val elapsedTime = (System.currentTimeMillis() - startTimer) / 1000
                updateTimer(elapsedTime)
            }
        }
    }

    private fun updateTimer(elapsedTime: Long) {
        if (elapsedTime >= timer.totalTime) {
            timerJob?.cancel()
            _uiState.value = TimerUiState.Completed
            viewModelScope.launch {
                timerSoundsPlayer.playTwice()
            }
            return
        }
        var accumulated = 0L
        for ((index, interval) in timer.intervals.withIndex()) {
            if (elapsedTime < accumulated + interval.time) {
                val remaining = accumulated + interval.time - elapsedTime
                if (index != lastIntervalIndex && lastIntervalIndex != -1) {
                    timerSoundsPlayer.playOneTime()
                }
                lastIntervalIndex = index
                _uiState.value = TimerUiState.Running(
                    activeIntervalIndex = index,
                    timeToEndInterval = remaining,
                    spentTime = elapsedTime
                )
                return
            }
            accumulated += interval.time
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}