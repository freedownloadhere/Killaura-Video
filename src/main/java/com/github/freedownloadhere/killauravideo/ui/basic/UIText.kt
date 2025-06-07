package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager

class UIText(
    defaultText: String = "Text"
)   : UI(),
    IDrawable,
    ILayoutPost
{
    enum class Scale(val numeric: Double) {
        SMALL(1.5),
        MEDIUM(2.0),
        LARGE(3.5)
    }

    var scale: Scale = Scale.MEDIUM
    var source: () -> String = { defaultText }

    val text: String
        get() = source()

    override var hidden: Boolean = false
    override var baseColor: UIColorEnum = UIColorEnum.WHITE

    override fun renderCallback() {
        if (text.isEmpty()) return
        GlobalManager.core!!.renderer.withTextState {
            val fr = Minecraft.getMinecraft().fontRendererObj
            GlStateManager.pushMatrix()
            GlStateManager.scale(scale.numeric, scale.numeric, 1.0)
            fr.drawStringWithShadow(text, 0.0f, 0.0f, baseColor.toPackedARGB())
            GlStateManager.popMatrix()
        }
    }

    override fun layoutPostCallback() {
        val fr = Minecraft.getMinecraft().fontRendererObj
        width = scale.numeric * fr.getStringWidth(text).toDouble()
        height = scale.numeric * fr.FONT_HEIGHT.toDouble()
    }
}