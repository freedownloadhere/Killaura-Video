package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.core.Core
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPre
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.core.LayoutUtils
import com.github.freedownloadhere.killauravideo.utils.ColorHelper

class UITitleBar(title : String)
    : UI(), IDrawable, ILayoutPre, IParent
{
    override var baseColor = ColorHelper.GuiNeutralDark
    override val children = listOf(UIText(title))

    override fun applyLayoutPre()  {
        val rect = LayoutUtils.Rectangle(this)
        LayoutUtils.scaleIn(children.first(), rect, 0.5)
        LayoutUtils.centerIn(children.first(), rect)
    }

    override fun draw() { Core.renderer.drawBasicBG(this) }
}