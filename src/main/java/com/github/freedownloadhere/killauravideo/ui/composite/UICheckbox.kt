package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBasicDraw
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import net.minecraft.client.renderer.GlStateManager
import kotlin.math.max

class UICheckbox: UI(), IClickable, IDrawable, IPadded, ILayoutPost {
    var checked: Boolean = false
    var onCheck: () -> Unit = { }

    override fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        onCheck()
        checked = !checked
    }

    override var hidden: Boolean = false
    override var baseColor: UIColorEnum = UIColorEnum.NEUTRAL
    override var padding: Double = 20.0

    override fun renderCallback() {
        uiBasicDraw()
        if(!checked) return
        GlStateManager.pushMatrix()
        GlStateManager.translate(0.3 * padding, 0.3 * padding, 1.0)
        GlStateManager.scale(width - 0.6 * padding, height - 0.6 * padding, 0.0)
        GlobalManager.core!!.renderer.drawRect(UIColorEnum.WHITE, filled = true)
        GlStateManager.popMatrix()
    }

    override fun layoutPostCallback() {
        width = max(width, 2.0 * padding)
        height = max(height, 2.0 * padding)
    }
}