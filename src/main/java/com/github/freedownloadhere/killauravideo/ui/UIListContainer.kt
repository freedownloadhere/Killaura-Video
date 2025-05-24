package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.core.Core
import com.github.freedownloadhere.killauravideo.ui.core.LayoutUtils
import com.github.freedownloadhere.killauravideo.ui.interfaces.*
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import kotlin.math.max
import kotlin.math.min

class UIListContainer
    : UI(), IScrollable, IDrawable, ILayoutPre, ILayoutPost, IParentExtendable, ISpecialTranslate
{
    private var start = 0.0
    private var listHeight = 0.0

    override var baseColor = ColorHelper.GuiNeutral
    override val children = mutableListOf<UI>()

    override fun applyLayoutPre() {
        for(child in children)
            when(child) {
                is UIListContainer -> {
                    LayoutUtils.setAspectRatio(child, 2.0)
                    LayoutUtils.scaleIn(child, LayoutUtils.Rectangle(this).scale(1.0 - 2.0 * Core.config.listSpacingScale))
                }
                is UITextBox -> {
                    LayoutUtils.setAspectRatio(child, 2.0)
                    LayoutUtils.scaleHeightTo(child, 0.1 * height)
                }
                is UITextButton -> {
                    LayoutUtils.setAspectRatio(child, 2.0)
                    LayoutUtils.scaleHeightTo(child, 0.1 * height)
                }
            }
    }

    override fun applyLayoutPost() {
        val listScale = Core.config.listSpacingScale
        listHeight = LayoutUtils.list(this, listScale, listScale, start)
    }

    override fun doSpecialTranslate(dx: Double, dy: Double) { start += dy }

    override fun draw() {
        Core.renderer.drawBasicBG(this)
    }

    override fun onScroll(d: Int) {
        if(listHeight <= height)
            return
        start += d
        start = min(start, relY)
        start = max(start, relY - listHeight + height)
    }

    override fun update(deltaTime: Long) {
        Core.renderer.scissorStack.push(this)
        for(child in children) {
            if(child.relY + child.height <= relY || child.relY >= relY + height)
                child.disable()
            else
                child.enable()
        }
        super.update(deltaTime)
        Core.renderer.scissorStack.pop()
    }
}