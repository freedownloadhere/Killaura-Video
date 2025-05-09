package com.github.freedownloadhere.killauravideo.rendering

import java.awt.Color

enum class ColorEnum(
    r: Int,
    g: Int,
    b: Int
) {
    RED(255, 0, 0),
    GREEN(0, 255, 0);

    val solid = Color(r, g, b, 255)
    val translucent = Color(r, g, b, 80)
}
