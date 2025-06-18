package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class UIIcon(config: UIStyleConfig): UI(config), ILayoutPost {
    enum class Scale(val numeric: Float) {
        SMALL(20.0f),
        MEDIUM(35.0f),
        LARGE(50.0f)
    }

    var scale: Scale = Scale.MEDIUM
    override var hidden: Boolean = false

    override fun renderCallback(info: UINewRenderer.RenderInfo) {
        RenderingBackend.drawRect(
            info.absX + relX,
            info.absY + relY,
            info.layer * 0.01f,
            width, height,
            UIColorEnum.BOX_SECONDARY, UIColorEnum.BOX_TERNARY,
            info.config.rounding,
            info.config.bordering,
            "checkmark"
        )
    }

    override fun layoutPostCallback() {
        width = scale.numeric
        height = scale.numeric
    }
}