package com.github.freedownloadhere.killauravideo.ui.widgets.basic

import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig

class UIWidgetIcon(config: UIStyleConfig): UIWidget(config), ILayoutPost {
    enum class Scale(val numeric: Float) {
        SMALL(20.0f),
        MEDIUM(35.0f),
        LARGE(50.0f)
    }

    var scale: Scale = Scale.MEDIUM

    override fun renderCallback(ri: IRenderInfo) {
        RenderingBackend.drawRect(
            ri.absX + relX,
            ri.absY + relY,
            width, height,
            config.colorBoxSecondary, config.colorBoxTernary,
            ri.config.rounding,
            ri.config.bordering,
            "checkmark"
        )
    }

    override fun layoutPostCallback() {
        width = scale.numeric
        height = scale.numeric
    }
}