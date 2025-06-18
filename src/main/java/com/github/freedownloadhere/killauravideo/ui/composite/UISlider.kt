package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import kotlin.math.floor
import kotlin.math.max

class UISlider(config: UIStyleConfig): UI(config), IPadded, IDrawable, ILayoutPost, IMouseEvent
{
    override var padding: Float = config.padding

    var minValue: Float = 0.0f
    var maxValue: Float = 1.0f
    private var position: Float = 0.0f

    var selectedValue: Float
        get() = minValue + (maxValue - minValue) * position
        set(value) { position = (value - minValue) / (maxValue - minValue) }

    var clickAction: () -> Unit = { }

    var segmented: Boolean = true
    var segmentCount: Int = 5

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmGrabbed?.ui != this)
            return
        val relX = mouseInfo.lastX - mouseInfo.lcmGrabbed!!.absX
        position = (relX / width).coerceIn(0.0f, 1.0f)
        if(segmented) {
            val segmentLength = 1.0f / segmentCount
            position = floor(position / segmentLength) * segmentLength
        }
        clickAction()
    }

    override var hidden: Boolean = false
    override fun renderCallback(info: UINewRenderer.RenderInfo) {
        RenderingBackend.drawLine(
            info.absX + relX + 0.0f,
            info.absY + relY + 0.5f * height - 1.0f,
            info.layer * 0.01f,
            info.absX + relX + width,
            info.absY + relY + 0.5f * height - 1.0f,
            info.layer * 0.01f,
            UIColorEnum.TEXT_SECONDARY,
            2.0f
        )

        for(i in 0..segmentCount) {
            RenderingBackend.drawLine(
                info.absX + relX + (width * i) / segmentCount,
                info.absY + relY + height * 0.25f,
                info.layer * 0.01f,
                info.absX + relX + (width * i) / segmentCount,
                info.absY + relY + height * 0.75f,
                info.layer * 0.01f,
                UIColorEnum.TEXT_SECONDARY,
                2.0f
            )
        }

        RenderingBackend.drawLine(
            info.absX + relX + width * position,
            info.absY + relY + 0.0f,
            info.layer * 0.01f,
            info.absX + relX + width * position,
            info.absY + relY + height,
            info.layer * 0.01f,
            UIColorEnum.ACCENT,
            4.0f
        )
    }

    override fun layoutPostCallback() {
        width = max(width, 2.0f * padding)
        height = max(height, 2.0f * padding)
    }
}