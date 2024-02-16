package com.pierre.wearosstopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pierre.wearosstopwatch.stopwatch.presentation.StopWatchViewModel
import com.pierre.wearosstopwatch.stopwatch.presentation.screen.StopWatchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            val viewModel: StopWatchViewModel = viewModel()
            val timerState by viewModel.timerState.collectAsStateWithLifecycle()
            val stopWatchText by viewModel.stopWatchText.collectAsStateWithLifecycle()
            StopWatchScreen(
                timerState = timerState,
                stopWatchText = stopWatchText,
                onToggleIsRunning = viewModel::onToggleRunning,
                onResetTimer = viewModel::onResetTimer
            )
        }
    }
}


