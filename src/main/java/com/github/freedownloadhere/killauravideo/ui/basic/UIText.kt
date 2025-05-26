package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager

open class UIText(
    initialText: String = ""
): UI(), IDrawable
{
    var scale = Scale.MEDIUM

    enum class Scale(val numeric: Double) {
        SMALL(1.5),
        MEDIUM(2.0),
        LARGE(3.5),
        VERY_LARGE(5.0)
    }

    var text: String = "Text"
        set(value) {
            field = value
            val fr = Minecraft.getMinecraft().fontRendererObj
            width = scale.numeric * fr.getStringWidth(field).toDouble()
            height = scale.numeric * fr.FONT_HEIGHT.toDouble()
        }

    init {
        text = initialText
    }

    override var baseColor = ColorHelper.White

    override fun draw() {
        if(text.isEmpty()) return

        GlobalManager.core!!.renderer.withTextState {
            val fr = Minecraft.getMinecraft().fontRendererObj
            GlStateManager.pushMatrix()
            GlStateManager.scale(scale.numeric, scale.numeric, 1.0)
            fr.drawStringWithShadow(text, 0.0f, 0.0f, baseColor.toPackedARGB())
            GlStateManager.popMatrix()
        }
    }
}