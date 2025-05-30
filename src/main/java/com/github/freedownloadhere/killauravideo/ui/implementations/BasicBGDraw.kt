package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum

class BasicBGDraw<T>
    : IDrawable
        where T: UI
{
    override var hidden = false
    override var baseColor = UIColorEnum.NEUTRAL

    @Suppress("UNCHECKED_CAST")
    override fun draw() {
         with(this as T) {
            GlobalManager.core?.renderer?.drawBasicBG(this, baseColor)
        }
    }
}