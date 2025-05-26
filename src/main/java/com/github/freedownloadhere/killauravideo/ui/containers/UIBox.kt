package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.utils.ColorHelper

abstract class UIBox: UI(), ILayoutPost, IParent, IDrawable
{
    var padded = true

    override fun applyLayoutPost() {
        stretchToFit()
    }

    override var baseColor = ColorHelper.GuiNeutral
    override fun draw() {
        GlobalManager.core!!.renderer.drawBasicBG(this)
    }

    protected abstract fun stretchToFit()
}