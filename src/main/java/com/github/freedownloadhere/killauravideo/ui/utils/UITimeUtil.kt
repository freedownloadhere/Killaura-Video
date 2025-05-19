package com.github.freedownloadhere.killauravideo.ui.utils

import java.time.Instant

class UITimeUtil {
    private var lastTime = Instant.now().toEpochMilli()
    fun newDeltaTime() : Long {
        val currTime = Instant.now().toEpochMilli()
        val deltaTime = currTime - lastTime
        lastTime = currTime
        return deltaTime
    }
}