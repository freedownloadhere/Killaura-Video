package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer

fun <T> T.uiBasicDraw(renderer: Renderer) where T: UI {
    renderer.drawBasicBG(this, renderer.baseRectangleColor)
}