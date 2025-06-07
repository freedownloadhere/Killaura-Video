package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation

class UIIcon(config: UIConfig) : UI(config), IDrawable, ILayoutPost {
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

    override fun renderCallback(renderer: Renderer) {
        GlStateManager.pushMatrix()
        GlStateManager.scale(width, height, 0.0)
        renderer.drawRect(iconLocation, filled = true)
        GlStateManager.popMatrix()
    }

    override fun layoutPostCallback() {
        width = scale.numeric
        height = scale.numeric
    }
}