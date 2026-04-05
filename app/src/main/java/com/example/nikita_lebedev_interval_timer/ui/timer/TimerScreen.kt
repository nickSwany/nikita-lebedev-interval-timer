package com.example.nikita_lebedev_interval_timer.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nikita_lebedev_interval_timer.ui.theme.Bg


@Composable
fun TimerScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Bg)) {
        Text(text = "Timer Screen")
    }
}