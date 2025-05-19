package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.utils.UILayoutUtils
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
            when(child::class) {
                UIListContainer::class -> {
                    UILayoutUtils.setAspectRatio(child, 2.0)
                    UILayoutUtils.scaleIn(child, UILayoutUtils.Rectangle(this).scale(1.0 - 2.0 * UICore.config.listSpacingScale))
                }
                UITextBox::class -> {
                    UILayoutUtils.setAspectRatio(child, 2.0)
                    UILayoutUtils.scaleHeightTo(child, 0.1 * h)
                }
                UITextButton::class -> {
                    UILayoutUtils.setAspectRatio(child, 2.0)
                    UILayoutUtils.scaleHeightTo(child, 0.1 * h)
                }
            }
    }

    override fun applyLayoutPost() {
        val listScale = UICore.config.listSpacingScale
        listHeight = UILayoutUtils.list(this, listScale, listScale, start)
    }

    override fun doSpecialTranslate(dx: Double, dy: Double) { start += dy }

    override fun draw() {
        UICore.renderer.drawBasicBG(this)
    }

    override fun onScroll(d: Int) {
        if(listHeight <= h)
            return
        start += d
        start = min(start, y)
        start = max(start, y - listHeight + h)
    }

    override fun update(deltaTime: Long) {
        UICore.renderer.scissorStack.push(this)
        for(child in children) {
            if(child.y + child.h <= y || child.y >= y + h)
                child.disable()
            else
                child.enable()
        }
        super.update(deltaTime)
        UICore.renderer.scissorStack.pop()
    }
}