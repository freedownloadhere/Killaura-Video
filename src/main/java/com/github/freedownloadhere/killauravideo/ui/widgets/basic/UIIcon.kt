package com.github.freedownloadhere.killauravideo.ui.widgets.basic

import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class UIIcon(config: UIStyleConfig): UI(config), ILayoutPost {
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
            ri.layer * 0.01f,
            width, height,
            UIColorEnum.BOX_SECONDARY, UIColorEnum.BOX_TERNARY,
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