package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import net.minecraft.util.ResourceLocation

class UIIcon(config: UIConfig): UI(config), ILayoutPost {
    enum class Scale(val numeric: Double) {
        SMALL(20.0),
        MEDIUM(35.0),
        LARGE(50.0)
    }

    companion object {
        var iconLocation = ResourceLocation("killauravideo", "textures/gui/check.png")
    }

    var scale: Scale = Scale.MEDIUM
    override var hidden: Boolean = false

    override fun renderCallback(info: UINewRenderer.RenderInfo) {
//        GlStateManager.pushMatrix()
//        GlStateManager.scale(width, height, 0.0)
//        renderer.drawRect(iconLocation, filled = true)
//        GlStateManager.popMatrix()
    }

    override fun layoutPostCallback() {
        width = scale.numeric
        height = scale.numeric
    }
}