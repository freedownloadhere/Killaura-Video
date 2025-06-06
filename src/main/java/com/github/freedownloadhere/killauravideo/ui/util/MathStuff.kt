package com.github.freedownloadhere.killauravideo.ui.util

import kotlin.math.floor
import kotlin.math.pow

// Might overflow if big number but Eh
fun Double.round(digits: Int): Double {
    val pow10 = 10.0.pow(digits)
    return floor(this * pow10) / pow10
}