package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.containers.UICenteredBox
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import com.github.freedownloadhere.killauravideo.ui.interfaces.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.IHoverable
import kotlin.math.max

open class UIButton: UICenteredBox<UIText>(UIText()), IClickable, IHoverable, IDrawable
{
    var text: String
        get() = child.text
        set(value) { child.text = value }

    var action: ()->Unit = {  }
    private var clickCooldown = 0L
    override var baseColor = ColorHelper.GuiNeutral

    override fun update(deltaTime: Long) {
        clickCooldown = max(0L, clickCooldown - deltaTime)
        super.update(deltaTime)
    }

    override fun draw() {
        GlobalManager.core?.renderer?.drawBasicBG(this)
    }

    override fun onClick(button: Int) {
        if(button == 0 && clickCooldown == 0L) {
            action()
            clickCooldown = GlobalManager.core!!.config.buttonClickCooldown
        }
    }

    override fun onHoverStart() { baseColor = ColorHelper.GuiNeutralLight }

    override fun onHoverStop() { baseColor = ColorHelper.GuiNeutral }
}