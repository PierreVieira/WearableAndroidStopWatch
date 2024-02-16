package com.pierre.wearosstopwatch.stopwatch.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.pierre.wearosstopwatch.stopwatch.presentation.TimerState

@Composable
fun StopWatchScreen(
    timerState: TimerState,
    stopWatchText: String,
    onToggleIsRunning: () -> Unit,
    onResetTimer: () -> Unit,
) {
    Scaffold(
        timeText = {
            TimeText(
                timeTextStyle = TimeTextDefaults.timeTextStyle(
                    fontSize = 10.sp
                )
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        }
    ) {
        StopWatchContent(
            modifier = Modifier.fillMaxSize(),
            state = timerState,
            text = stopWatchText,
            onToggleIsRunning = onToggleIsRunning,
            onResetTimer = onResetTimer
        )
    }
}
