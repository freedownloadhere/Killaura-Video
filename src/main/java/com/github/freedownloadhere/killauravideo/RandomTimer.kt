package com.github.freedownloadhere.killauravideo

import java.time.Instant
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random
import kotlin.random.asJavaRandom

class RandomTimer(
    private var mean: Long,
    private var deviation: Long,
    private var outlierProbability : Double
) {
    private var msLeft = 0L
    private var msLastUpdate = Instant.now().toEpochMilli()

    init {
        allTimers.add(this)
    }

    companion object {
        private val allTimers = mutableListOf<RandomTimer>()
        private val randomizer = Random.asJavaRandom()

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
        var baseSample = nextGaussian()
        if(randomizer.nextDouble() <= outlierProbability)
            baseSample *= 2L
        msLeft = baseSample
    }

    fun hasFinished(): Boolean = (msLeft == 0L)

    private fun nextGaussian(): Long = abs((randomizer.nextGaussian() * deviation + mean).toLong())
}