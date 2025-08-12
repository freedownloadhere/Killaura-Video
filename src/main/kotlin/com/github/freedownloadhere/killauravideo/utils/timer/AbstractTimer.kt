package com.github.freedownloadhere.killauravideo.utils.timer

import java.time.Instant
import kotlin.math.max

abstract class AbstractTimer {
    protected var msLeft = 0L
    private var msLastUpdate = Instant.now().toEpochMilli()

    fun update() {
        val msNow = Instant.now().toEpochMilli()
        val msDelta = msNow - msLastUpdate
        msLastUpdate = msNow
        msLeft = max(0L, msLeft - msDelta)
    }

    fun hasFinished(): Boolean = (msLeft == 0L)

    abstract fun reset()
}