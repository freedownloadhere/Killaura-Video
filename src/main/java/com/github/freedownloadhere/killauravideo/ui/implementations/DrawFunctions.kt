package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable

fun <T> T.uiBasicDraw() where T: UI, T: IDrawable {
    GlobalManager.core!!.renderer.drawBasicBG(this, baseColor)
}