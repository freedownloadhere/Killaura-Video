package com.github.freedownloadhere.killauravideo.utils.timer

class FixedTimer(private val msTime: Long) : AbstractTimer() {
    override fun reset() {
        msLeft = msTime
    }
}