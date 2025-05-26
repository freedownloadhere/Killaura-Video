package com.github.freedownloadhere.killauravideo.ui.interfaces.render

import com.github.freedownloadhere.killauravideo.utils.ColorHelper

interface IDrawable {
    var hidden: Boolean
    var baseColor: ColorHelper
    fun draw()
}