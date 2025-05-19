package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPre
import com.github.freedownloadhere.killauravideo.ui.utils.UITextUtils
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager

open class UITextWrap(
    private val str : String,
    private val parent : UI,
    private val scaleMult : Double = 1.0
)
    : UI(), IDrawable, ILayoutPre
{
    private var rows = listOf<String>()
    private val ts = scaleMult * UICore.config.textScale

    override fun applyLayoutPre() {
        rows = UITextUtils.wordWrap(str, parent, scaleMult)
        h = 0.0
        for(row in rows)
            h += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * ts
    }

    override var baseColor = ColorHelper.White
    override fun draw() {
        UICore.renderer.beginTextState()
        val fr = Minecraft.getMinecraft().fontRendererObj
        val fontHeight = fr.FONT_HEIGHT
        var renderY = y
        for(row in rows) {
            GlStateManager.pushMatrix()
            GlStateManager.translate(x, renderY, 0.0)
            GlStateManager.scale(ts, ts, 1.0)
            fr.drawStringWithShadow(row, 0.0f, 0.0f, baseColor.toPackedARGB())
            GlStateManager.popMatrix()
            renderY += fontHeight * ts
        }
        UICore.renderer.endTextState()
    }
}