package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import com.github.freedownloadhere.killauravideo.ui.interfaces.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.IHoverable
import kotlin.math.max

open class UIButton(private val callback: () -> Unit)
    : UI(), IClickable, IHoverable, IDrawable
{
    private var clickCooldown = 0L

    override fun update(deltaTime: Long) {
        clickCooldown = max(0L, clickCooldown - deltaTime)
        super.update(deltaTime)
    }

    override var baseColor = ColorHelper.GuiNeutral

    override fun draw() {
        UICore.renderer.drawBasicBG(this)
    }

    override fun onClick(button: Int) {
        if(button == 0 && clickCooldown == 0L) {
            callback()
            clickCooldown = UICore.config.buttonClickCooldown
        }
    }

    override fun onHoverStart() { baseColor = ColorHelper.GuiNeutralLight }

    override fun onHoverStop() { baseColor = ColorHelper.GuiNeutral }
}