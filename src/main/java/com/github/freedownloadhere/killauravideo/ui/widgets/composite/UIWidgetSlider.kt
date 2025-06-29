package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import kotlin.math.floor
import kotlin.math.max

class UIWidgetSlider(config: UIStyleConfig): UIWidget(config), ILayoutPost, IMouseEvent
{
    var minValue: Float = 0.0f
    var maxValue: Float = 1.0f
    private var position: Float = 0.0f

    var selectedValue: Float
        get() = minValue + (maxValue - minValue) * position
        set(value) { position = (value - minValue) / (maxValue - minValue) }

    var clickAction: () -> Unit = { }

    var snapToSegment: Boolean = false
    var segmentCount: Int = 5

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmGrabbed?.uiWidget != this)
            return
        val relX = mouseInfo.lastX - mouseInfo.lcmGrabbed!!.absX
        position = (relX / width).coerceIn(0.0f, 1.0f)
        if(snapToSegment) {
            val segmentLength = 1.0f / segmentCount
            position = floor(position / segmentLength) * segmentLength
        }
        clickAction()
    }

    override fun renderCallback(ri: IRenderInfo) {
        RenderingBackend.translateBy(ri, relX, relY)

        RenderingBackend.drawLine(
            0.0f,
            0.5f * height - 1.0f,
            width,
            0.5f * height - 1.0f,
            ri.config.colorTextSecondary,
            2.0f
        )

        for(i in 0..segmentCount) {
            RenderingBackend.drawLine(
                (width * i) / segmentCount,
                height * 0.25f,
                (width * i) / segmentCount,
                height * 0.75f,
                ri.config.colorTextSecondary,
                2.0f
            )
        }

        RenderingBackend.drawLine(
            width * position,
            0.0f,
            width * position,
            height,
            ri.config.colorAccent,
            4.0f
        )
    }

    override fun layoutPostCallback() {
        width = max(width, config.minSliderWidth)
        height = max(height, config.minSliderHeight)
    }
}