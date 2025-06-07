package com.github.freedownloadhere.killauravideo.ui.interfaces.render

import com.github.freedownloadhere.killauravideo.utils.UIColorEnum

interface IDrawable {
    var hidden: Boolean
    var baseColor: UIColorEnum
    fun renderCallback()
}