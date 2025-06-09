package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import net.minecraft.client.Minecraft

class UIText(config: UIConfig, default: String = "Text"): UI(config), IDrawable, ILayoutPost
{
    enum class Scale(val numeric: Double) {
        SMALL(1.5),
        MEDIUM(2.0),
        LARGE(3.5)
    }

    var scale: Scale = Scale.MEDIUM
    var source: () -> String = { default }

    val text: String
        get() = source()

    override var hidden: Boolean = false
    var baseColor: UIColorEnum = UIColorEnum.TEXT_PRIMARY

    override fun renderCallback(info: UINewRenderer.RenderInfo) {
        if (text.isEmpty()) return
        RenderingBackend.drawText(
            string = text,
            color = baseColor,
            scale = scale
        )
    }

    override fun layoutPostCallback() {
        val fr = Minecraft.getMinecraft().fontRendererObj
        width = scale.numeric * fr.getStringWidth(text).toDouble()
        height = scale.numeric * fr.FONT_HEIGHT.toDouble()
    }
}