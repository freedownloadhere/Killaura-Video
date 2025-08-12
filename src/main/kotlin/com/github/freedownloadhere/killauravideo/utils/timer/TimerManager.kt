package com.github.freedownloadhere.killauravideo.utils.timer

class TimerManager {
    private val allTimers = mutableListOf<AbstractTimer>()

    fun update() {
        for(timer in allTimers)
            timer.update()
    }

    fun newTimer(msTime: Long): FixedTimer {
        val timer = FixedTimer(msTime)
        allTimers.add(timer)
        return timer
    }

    fun newRandomTimer(msMean: Long, msDeviation: Long, outlierP: Double): RandomTimer {
        val randomTimer = RandomTimer(msMean, msDeviation, outlierP)
        allTimers.add(randomTimer)
        return randomTimer
    }
}