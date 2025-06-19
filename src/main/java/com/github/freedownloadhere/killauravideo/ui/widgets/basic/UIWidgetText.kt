package com.github.freedownloadhere.killauravideo.ui.widgets.basic

import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import java.awt.Color

class UIWidgetText(config: UIStyleConfig, default: String = "Text"): UIWidget(config), ILayoutPost
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

    var color: Color = config.colorTextPrimary

    override fun renderCallback(ri: IRenderInfo) {
        if (text.isEmpty()) return
        RenderingBackend.translateBy(ri, relX, relY)
        RenderingBackend.drawText(
            x = 0.0f,
            y = 0.0f,
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