package com.github.freedownloadhere.killauravideo.ui.core

import java.awt.Color

data class UIStyleConfig(
    var screenWidth: Float = 1920.0f,
    var screenHeight: Float = 1080.0f,

    var buttonClickCooldown: Long = 100L,

    var padding: Float = 10.0f,

    var rounding: Float = 5.0f,
    var bordering: Float = 1.0f,

    var colorBoxPrimary: Color = Color(51, 51, 51),
    var colorBoxSecondary: Color = Color(26, 26, 26),
    var colorBoxTernary: Color = Color(0, 0, 0),

    var colorTextPrimary: Color = Color(255, 255, 255),
    var colorTextSecondary: Color = Color(132, 132, 132),
    var colorTextTernary: Color = Color(102, 102, 102),

    var colorAccent: Color = Color(109, 56, 255),

    var minSliderWidth: Float = 100.0f,
    var minSliderHeight: Float = 20.0f,
)