package com.example.nikita_lebedev_interval_timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nikita_lebedev_interval_timer.ui.loading.LoadingScreen
import com.example.nikita_lebedev_interval_timer.ui.theme.NikitalebedevintervaltimerTheme
import com.example.nikita_lebedev_interval_timer.ui.timer.TimerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NikitalebedevintervaltimerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "loading"
                ) {
                    composable("loading") {
                        LoadingScreen(
                            onNavigateToTimer = {
                                navController.navigate("timer")
                            }
                        )
                    }
                    composable("timer") {
                        TimerScreen()
                    }
                }
            }
        }
    }
}