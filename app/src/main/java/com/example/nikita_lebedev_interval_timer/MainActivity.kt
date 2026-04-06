package com.example.nikita_lebedev_interval_timer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nikita_lebedev_interval_timer.ui.loading.LoadingScreen
import com.example.nikita_lebedev_interval_timer.ui.theme.NikitalebedevintervaltimerTheme
import com.example.nikita_lebedev_interval_timer.ui.timer.TimerScreen
import com.google.gson.Gson
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
                            onNavigateToTimer = { timer ->
                                val json = Uri.encode(Gson().toJson(timer))
                                navController.navigate("timer/$json")
                            }
                        )
                    }
                    composable(
                        route = "timer/{timerJson}",
                        arguments = listOf(navArgument("timerJson") { type = NavType.StringType })
                    ) {
                        TimerScreen(navController = navController)
                    }
                }
            }
        }
    }
}