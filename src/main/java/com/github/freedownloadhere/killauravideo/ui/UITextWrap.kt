package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.core.Core
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPre
import com.github.freedownloadhere.killauravideo.ui.core.TextUtils
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
    private val ts = scaleMult * Core.config.textScale

    override fun applyLayoutPre() {
        rows = TextUtils.wordWrap(str, parent, scaleMult)
        height = 0.0
        for(row in rows)
            height += Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * ts
    }

    override var baseColor = ColorHelper.White
    override fun draw() {
        Core.renderer.beginTextState()
        val fr = Minecraft.getMinecraft().fontRendererObj
        val fontHeight = fr.FONT_HEIGHT
        var renderY = relY
        for(row in rows) {
            GlStateManager.pushMatrix()
            GlStateManager.translate(relX, renderY, 0.0)
            GlStateManager.scale(ts, ts, 1.0)
            fr.drawStringWithShadow(row, 0.0f, 0.0f, baseColor.toPackedARGB())
            GlStateManager.popMatrix()
            renderY += fontHeight * ts
        }
        Core.renderer.endTextState()
    }
}