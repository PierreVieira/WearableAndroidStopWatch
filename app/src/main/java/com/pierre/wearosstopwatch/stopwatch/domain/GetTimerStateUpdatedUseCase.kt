package com.pierre.wearosstopwatch.stopwatch.domain

import com.pierre.wearosstopwatch.stopwatch.presentation.TimerState
import javax.inject.Inject

class GetTimerStateUpdatedUseCase @Inject constructor() {

    operator fun invoke(timerState: TimerState): TimerState = when (timerState) {
        TimerState.RUNNING -> TimerState.PAUSED
        TimerState.PAUSED,
        TimerState.RESET,
        -> TimerState.RUNNING
    }
}
