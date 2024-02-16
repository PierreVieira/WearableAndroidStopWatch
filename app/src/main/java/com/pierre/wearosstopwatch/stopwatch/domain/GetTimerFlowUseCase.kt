package com.pierre.wearosstopwatch.stopwatch.domain

import com.pierre.wearosstopwatch.stopwatch.presentation.TimerState
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTimerFlowUseCase @Inject constructor() {
    operator fun invoke(timerState: TimerState): Flow<Long> = flow {
        var startMillis = System.currentTimeMillis()
        while (timerState.isRunning()) {
            emit(
                getTimeDiff(
                    currentMillis = System.currentTimeMillis(),
                    startMillis = startMillis
                )
            )
            startMillis = System.currentTimeMillis()
            delay(EMISSION_DELAY)
        }
    }

    private fun TimerState.isRunning(): Boolean = this == TimerState.RUNNING

    private fun getTimeDiff(currentMillis: Long, startMillis: Long): Long =
        if (currentMillis > startMillis) {
            currentMillis - startMillis
        } else {
            INITIAL_TIME
        }

    companion object {
        private const val EMISSION_DELAY = 10L
        private const val INITIAL_TIME = 0L
    }
}
