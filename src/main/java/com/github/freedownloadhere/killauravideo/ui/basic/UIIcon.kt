package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import net.minecraft.util.ResourceLocation

class UIIcon(config: UIConfig): UI(config), ILayoutPost {
    enum class Scale(val numeric: Double) {
        SMALL(20.0),
        MEDIUM(35.0),
        LARGE(50.0)
    }

    var scale: Scale = Scale.MEDIUM
    override var hidden: Boolean = false

    override fun renderCallback(info: UINewRenderer.RenderInfo) {
        RenderingBackend.drawRect(
            (info.absX + relX).toFloat(),
            (info.absY + relY).toFloat(),
            info.layer * 0.01f,
            width.toFloat(), height.toFloat(),
            UIColorEnum.BOX_SECONDARY, UIColorEnum.BOX_TERNARY,
            info.config.rounding.toFloat(),
            info.config.bordering.toFloat(),
            "checkmark"
        )
    }

    override fun layoutPostCallback() {
        width = scale.numeric
        height = scale.numeric
    }
}