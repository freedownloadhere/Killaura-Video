package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

class UIText(config: UIConfig, default: String = "Text"): UI(config), IDrawable, ILayoutPost
{
    // duplicate data
    enum class Scale(val idx: Int, val vSizePx: Double) {
        VERY_SMALL(0, 15.0),
        SMALL(1, 25.0),
        MEDIUM(2, 40.0),
        LARGE(3, 55.0),
        VERY_LARGE(4, 70.0),
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
            x = (info.absX + relX).toFloat(),
            y = (info.absY + relY).toFloat(),
            z = info.layer * 0.01f,
            string = text,
            color = baseColor,
            scale = scale,
        )
    }

    override fun layoutPostCallback() {
        width = JavaNativeRendering.nGetTextWidth(text, scale.idx).toDouble()
        height = scale.vSizePx
    }
}