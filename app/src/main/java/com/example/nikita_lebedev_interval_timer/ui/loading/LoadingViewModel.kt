package com.example.nikita_lebedev_interval_timer.ui.loading

import androidx.lifecycle.ViewModel
import com.example.nikita_lebedev_interval_timer.domain.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val repository: TimerRepository
) : ViewModel() {

}