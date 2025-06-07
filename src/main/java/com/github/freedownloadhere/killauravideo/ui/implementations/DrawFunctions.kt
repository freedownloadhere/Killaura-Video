package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum

fun <T> T.uiBoxDraw(renderer: Renderer, baseColor: UIColorEnum) where T: UI {
    val color = if(renderer.interactionManager.hovered == this)
        UIColorEnum.BOX_PRIMARY
    else
        baseColor
    renderer.drawBasicBG(this, color)
}