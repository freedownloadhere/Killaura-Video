package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IStretchable
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.utils.ColorHelper

abstract class UIBox: UI(), ILayoutPost, IParent, IDrawable
{
    var padded = true

    override var hidden = false
    override var baseColor = ColorHelper.GuiNeutral
    override fun draw() {
        GlobalManager.core!!.renderer.drawBasicBG(this)
    }
}