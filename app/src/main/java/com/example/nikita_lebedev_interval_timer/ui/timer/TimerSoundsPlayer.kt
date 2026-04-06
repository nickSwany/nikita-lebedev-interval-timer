package com.example.nikita_lebedev_interval_timer.ui.timer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject

class TimerSoundsPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private fun createPlayer(): MediaPlayer {
        return MediaPlayer.create(
            context,
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ).apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
        }
    }

    fun playOneTime() {
        val player = createPlayer()
        player.setOnCompletionListener {
            it.release()
        }
        player.start()
    }

    suspend fun playTwice() {
        val player1 = createPlayer()
        val player2 = createPlayer()
        player1.setOnCompletionListener {
            it.release()
        }
        player1.start()
        delay(500)
        player2.setOnCompletionListener {
            it.release()
        }
        player2.start()
    }
}