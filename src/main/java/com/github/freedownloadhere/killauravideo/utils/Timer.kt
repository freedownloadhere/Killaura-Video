package com.github.freedownloadhere.killauravideo.utils

import java.time.Instant
import kotlin.math.max

class Timer(
    private var msTime: Long
) {
    private var msLeft = 0L
    private var msLastUpdate = Instant.now().toEpochMilli()

    init {
        allTimers.add(this)
    }

    companion object {
        private val allTimers = mutableListOf<Timer>()

        fun updateAllTimers() {
            for(timer in allTimers)
                timer.update()
        }
    }

    fun update() {
        val msNow = Instant.now().toEpochMilli()
        val msDelta = msNow - msLastUpdate
        msLastUpdate = msNow
        msLeft = max(0L, msLeft - msDelta)
    }

    fun reset() {
        msLeft = msTime
    }

    fun hasFinished(): Boolean = (msLeft == 0L)
}