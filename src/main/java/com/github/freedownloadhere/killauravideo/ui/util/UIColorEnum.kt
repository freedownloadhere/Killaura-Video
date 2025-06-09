package com.github.freedownloadhere.killauravideo.ui.util

enum class UIColorEnum(val r: Int, val g: Int, val b: Int, val a: Int = 255) {
    BOX_PRIMARY(51, 51, 51),
    BOX_SECONDARY(26, 26, 26),
    BOX_TERNARY(0, 0, 0),

    TEXT_PRIMARY(255, 255, 255),
    TEXT_SECONDARY(132, 132, 132),
    TEXT_TERNARY(102, 102, 102),

    ACCENT(109, 56, 255);

    val rFloat: Float
        get() = r / 255.0f
    val gFloat: Float
        get() = g / 255.0f
    val bFloat: Float
        get() = b / 255.0f
    val aFloat: Float
        get() = a / 255.0f

    fun toPackedARGB(): Int = (a shl 24) or (r shl 16) or (g shl 8) or b
}