package com.github.freedownloadhere.killauravideo.utils

enum class UIColorEnum(val r: Int, val g: Int, val b: Int, val a: Int) {
    WHITE(255, 255, 255, 255),
    NEUTRAL_DARK(30, 30, 30, 255),
    NEUTRAL(50, 50, 50, 255),
    NEUTRAL_LIGHT(70, 70, 70, 255),
    PRIMARY(93, 43, 144, 255);

    fun toPackedARGB(): Int = (a shl 24) or (r shl 16) or (g shl 8) or b
}