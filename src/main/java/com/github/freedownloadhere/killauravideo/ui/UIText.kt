package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager

open class UIText(s : String)
    : UI(), IDrawable
{
    var str : String = ""
        set(value) {
            field = value
            val fr = Minecraft.getMinecraft().fontRendererObj
            w = fr.getStringWidth(field).toDouble() * UICore.config.textScale
            h = fr.FONT_HEIGHT.toDouble() * UICore.config.textScale
        }

    init {
        str = s
    }

    override var baseColor = ColorHelper.White
    override fun draw() {
        if(str.isEmpty())
            return

        UICore.renderer.beginTextState()

        val fr = Minecraft.getMinecraft().fontRendererObj
        val scaleMultX = w / fr.getStringWidth(str).toDouble()
        val scaleMultY = h / fr.FONT_HEIGHT
        GlStateManager.translate(x, y, 0.0)
        GlStateManager.scale(scaleMultX, scaleMultY, 1.0)
        fr.drawStringWithShadow(str, 0.0f, 0.0f, baseColor.toPackedARGB())

        UICore.renderer.endTextState()
    }
}