package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPre
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.utils.UILayoutUtils
import com.github.freedownloadhere.killauravideo.utils.ColorHelper

class UITitleBar(title : String)
    : UI(), IDrawable, ILayoutPre, IParent
{
    override var baseColor = ColorHelper.GuiNeutralDark
    override val children = listOf(UIText(title))

    override fun applyLayoutPre()  {
        val rect = UILayoutUtils.Rectangle(this)
        UILayoutUtils.scaleIn(children.first(), rect, 0.5)
        UILayoutUtils.centerIn(children.first(), rect)
    }

    override fun draw() { UICore.renderer.drawBasicBG(this) }
}