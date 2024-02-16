package com.pierre.wearosstopwatch.stopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pierre.wearosstopwatch.stopwatch.domain.GetConvertedTimeUseCase
import com.pierre.wearosstopwatch.stopwatch.domain.GetTimerFlowUseCase
import com.pierre.wearosstopwatch.stopwatch.domain.GetTimerStateUpdatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@ExperimentalCoroutinesApi
@HiltViewModel
class StopWatchViewModel @Inject constructor(
    private val getConvertedTime: GetConvertedTimeUseCase,
    private val getTimerFlow: GetTimerFlowUseCase,
    private val getTimerStateUpdated: GetTimerStateUpdatedUseCase
) : ViewModel() {

    private val _elapsedTime = MutableStateFlow(INITIAL_TIME)
    private val _timerState: MutableStateFlow<TimerState> = MutableStateFlow(TimerState.RESET)
    val timerState: StateFlow<TimerState> = _timerState


    val stopWatchText = _elapsedTime
        .map { millis ->
            getConvertedTime(millis)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = INITIAL_TIME_DISPLAYED
        )

    init {
        _timerState
            .flatMapLatest { timerState: TimerState ->
                getTimerFlow(timerState)
            }
            .onEach { timeDiff ->
                _elapsedTime.update { it + timeDiff }
            }
            .launchIn(viewModelScope)
    }

    fun onToggleRunning() {
        _timerState.update { timerState ->
            getTimerStateUpdated(timerState)
        }
    }

    fun onResetTimer() {
        _timerState.update { TimerState.RESET }
        _elapsedTime.update { INITIAL_TIME }
    }

    companion object {
        private const val STOP_TIMEOUT_MILLIS = 5_000L
        private const val INITIAL_TIME = 0L
        private const val INITIAL_TIME_DISPLAYED = "00:00:00.000"
    }
}
