package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickHoldable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL11
import kotlin.math.floor
import kotlin.math.max

class UISlider(config: UIConfig): UI(config), IPadded, IClickHoldable, IDrawable, ILayoutPost
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

    override fun clickHoldCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        LogManager.getLogger().info("$mouseRelX : $position")
        position = mouseRelX / width
        if(segmented) {
            val segmentLength = 1.0 / segmentCount
            position = floor(position / segmentLength) * segmentLength
        }
        clickAction()
    }

    override var hidden: Boolean = false
    override fun renderCallback(renderer: Renderer) {
        val tess = Tessellator.getInstance()
        val wr = tess.worldRenderer
        val lineWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH)
        val col = UIColorEnum.NEUTRAL_LIGHT
        GL11.glLineWidth(2.0f)
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(0.0, 0.5 * height - 1.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        wr.pos(width, 0.5 * height - 1.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        tess.draw()
        for(i in 0..segmentCount) {
            wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
            wr.pos((width * i) / segmentCount, height * 0.25, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
            wr.pos((width * i) / segmentCount, height * 0.75, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
            tess.draw()
        }
        GL11.glLineWidth(4.0f)
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(width * position, 0.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        wr.pos(width * position, height, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        tess.draw()
        GL11.glLineWidth(lineWidth)
    }

    override fun layoutPostCallback() {
        width = max(width, 2.0 * padding)
        height = max(height, 2.0 * padding)
    }
}