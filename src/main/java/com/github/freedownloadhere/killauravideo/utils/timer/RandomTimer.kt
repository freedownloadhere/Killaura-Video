package com.github.freedownloadhere.killauravideo.utils.timer

import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.asJavaRandom

class RandomTimer(
    private val msMean: Long,
    private val msDeviation: Long,
    private val outlierP: Double
) : AbstractTimer() {
    companion object {
        private val randomizer = Random.asJavaRandom()
    }

    override fun reset() {
        var baseSample = nextGaussian()
        if(randomizer.nextDouble() <= outlierP)
            baseSample *= 2L
        msLeft = baseSample
    }

    private fun nextGaussian(): Long = abs((randomizer.nextGaussian() * msDeviation + msMean).toLong())
}