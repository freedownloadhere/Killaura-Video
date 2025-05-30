package com.github.freedownloadhere.killauravideo.ui.interfaces.misc

interface IText {
    enum class Scale(val numeric: Double) {
        SMALL(1.5),
        MEDIUM(2.0),
        LARGE(3.5)
    }

    val text: String
    val scale: Scale
}