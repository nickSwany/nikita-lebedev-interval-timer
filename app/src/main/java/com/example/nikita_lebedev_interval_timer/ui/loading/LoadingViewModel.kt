package com.example.nikita_lebedev_interval_timer.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikita_lebedev_interval_timer.domain.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoadingUiState())
    val uiState: StateFlow<LoadingUiState> = _uiState.asStateFlow()

    private val _event = Channel<LoadingEvent>()
    val event = _event.receiveAsFlow()

    fun changeTimerId(id: String) {
        _uiState.update { it.copy(timerId = id, isError = false) }
    }

    fun loadInterval() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }

            repository.getTimerById(_uiState.value.timerId.toIntOrNull() ?: 0)
                .onSuccess { timer ->
                    _uiState.update { it.copy(isLoading = false, isError = false) }
                    _event.send(LoadingEvent.NavigateToTimer(timer))
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError =  true
                        )
                    }
                }
        }
    }
}