package com.github.freedownloadhere.killauravideo.ui.widgets.basic

import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class UIText(config: UIStyleConfig, default: String = "Text"): UI(config), ILayoutPost
{
    // duplicate data
    enum class Scale(val idx: Int, val vSizePx: Float) {
        VERY_SMALL(0, 15.0f),
        SMALL(1, 25.0f),
        MEDIUM(2, 40.0f),
        LARGE(3, 55.0f),
        VERY_LARGE(4, 70.0f),
    }

    var scale: Scale = Scale.MEDIUM
    var source: () -> String = { default }

    val text: String
        get() = source()

    var color: UIColorEnum = UIColorEnum.TEXT_PRIMARY

    override fun renderCallback(ri: IRenderInfo) {
        if (text.isEmpty()) return
        RenderingBackend.drawText(
            x = ri.absX + relX,
            y = ri.absY + relY,
            z = ri.layer * 0.01f,
            string = text,
            color = color,
            scale = scale,
        )
    }

    override fun layoutPostCallback() {
        width = JavaNativeRendering.nGetTextWidth(text, scale.idx)
        height = scale.vSizePx
    }
}