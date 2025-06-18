package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IGrabbable
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import kotlin.math.floor
import kotlin.math.max

class UISlider(config: UIConfig): UI(config), IPadded, IDrawable, ILayoutPost, IMouseEvent
{
    override var padding: Double = config.padding

    var minValue: Double = 0.0
    var maxValue: Double = 1.0
    private var position: Double = 0.0

    var selectedValue: Double
        get() = minValue + (maxValue - minValue) * position
        set(value) { position = (value - minValue) / (maxValue - minValue) }

    var clickAction: () -> Unit = { }

    var segmented: Boolean = true
    var segmentCount: Int = 5

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmGrabbed?.ui != this)
            return
        val relX = mouseInfo.lastX - mouseInfo.lcmGrabbed!!.absX
        position = (relX / width).coerceIn(0.0, 1.0)
        if(segmented) {
            val segmentLength = 1.0 / segmentCount
            position = floor(position / segmentLength) * segmentLength
        }
        clickAction()
    }

    override var hidden: Boolean = false
    override fun renderCallback(info: UINewRenderer.RenderInfo) {
        RenderingBackend.drawLine(
            info.absX.toFloat() + relX.toFloat() + 0.0f,
            info.absY.toFloat() + relY.toFloat() + 0.5f * height.toFloat() - 1.0f,
            info.layer * 0.01f,
            info.absX.toFloat() + relX.toFloat() + width.toFloat(),
            info.absY.toFloat() + relY.toFloat() + 0.5f * height.toFloat() - 1.0f,
            info.layer * 0.01f,
            UIColorEnum.TEXT_SECONDARY,
            2.0f
        )

        for(i in 0..segmentCount) {
            RenderingBackend.drawLine(
                info.absX.toFloat() + relX.toFloat() + (width.toFloat() * i) / segmentCount,
                info.absY.toFloat() + relY.toFloat() + height.toFloat() * 0.25f,
                info.layer * 0.01f,
                info.absX.toFloat() + relX.toFloat() + (width.toFloat() * i) / segmentCount,
                info.absY.toFloat() + relY.toFloat() + height.toFloat() * 0.75f,
                info.layer * 0.01f,
                UIColorEnum.TEXT_SECONDARY,
                2.0f
            )
        }

        RenderingBackend.drawLine(
            info.absX.toFloat() + relX.toFloat() + width.toFloat() * position.toFloat(),
            info.absY.toFloat() + relY.toFloat() + 0.0f,
            info.layer * 0.01f,
            info.absX.toFloat() + relX.toFloat() + width.toFloat() * position.toFloat(),
            info.absY.toFloat() + relY.toFloat() + height.toFloat(),
            info.layer * 0.01f,
            UIColorEnum.ACCENT,
            4.0f
        )
    }

    override fun layoutPostCallback() {
        width = max(width, 2.0 * padding)
        height = max(height, 2.0 * padding)
    }
}