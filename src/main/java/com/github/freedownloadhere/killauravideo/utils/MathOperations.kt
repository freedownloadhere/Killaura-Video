package com.github.freedownloadhere.killauravideo.utils

private const val ONE_EIGHTY_OVER_PI = 57.29577951308232
private const val PI_OVER_ONE_EIGHTY = 0.017453292f

fun Double.toDegrees(): Double {
    return this * ONE_EIGHTY_OVER_PI
}

fun Float.toRadians(): Float {
    return this * PI_OVER_ONE_EIGHTY
}

fun Float.cropAngle180(): Float {
    var angle = this
    while(angle < -180.0f) angle += 360.0f
    while(angle >= 180.0f) angle -= 360.0f
    return angle
}