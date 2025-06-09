package com.github.freedownloadhere.killauravideo.ui.interfaces.render

import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer

interface IDrawable {
    var hidden: Boolean
    fun renderCallback(info: UINewRenderer.RenderInfo)
}