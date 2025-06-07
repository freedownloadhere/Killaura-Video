package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import net.minecraft.client.renderer.GlStateManager

class UIIcon: UI(), IDrawable, ILayoutPost {
    enum class Scale(val numeric: Double) {
        SMALL(20.0),
        MEDIUM(35.0),
        LARGE(50.0)
    }

    var scale: Scale = Scale.MEDIUM
    override var hidden: Boolean = false

    override fun renderCallback(renderer: Renderer) {
        GlStateManager.pushMatrix()
        GlStateManager.scale(width, height, 0.0)
        renderer.drawRect(UIColorEnum.WHITE, filled = true)
        GlStateManager.popMatrix()
    }

    override fun layoutPostCallback() {
        width = scale.numeric
        height = scale.numeric
    }
}