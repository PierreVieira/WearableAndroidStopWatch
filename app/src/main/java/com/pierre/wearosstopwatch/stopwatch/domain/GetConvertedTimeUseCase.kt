package com.pierre.wearosstopwatch.stopwatch.domain

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetConvertedTimeUseCase @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern(TIME_PATTERN)

    operator fun invoke(millis: Long) = LocalTime
        .ofNanoOfDay(millis * SECOND_IN_MILLIS)
        .format(formatter)

    companion object {
        private const val SECOND_IN_MILLIS = 1_000_000
        private const val TIME_PATTERN = "HH:mm:ss.SSS"
    }
}
