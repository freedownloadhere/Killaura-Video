package com.github.freedownloadhere.killauravideo.ui.interfaces.render

import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer

interface IDrawable {
    var hidden: Boolean
    fun renderCallback(renderer: Renderer)
}