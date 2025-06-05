package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.apache.logging.log4j.LogManager
import org.lwjgl.opengl.GL11
import kotlin.math.max

class UISlider
    : UI(),
    IPadded,
    IClickable,
    IDrawable,
    ILayoutPost
{
    override var padding: Double = GlobalManager.core!!.config.padding

    var minValue: Double = 0.0
    var maxValue: Double = 1.0
    private var position: Double = 0.0
    val selectedValue: Double
        get() = minValue + (maxValue - minValue) * position

    var clickAction: () -> Unit = { }

    override fun onClick(button: Int, mouseRelX: Double, mouseRelY: Double) {
        LogManager.getLogger().info("$mouseRelX : $position")
        position = mouseRelX / width
        clickAction()
    }

    override var hidden: Boolean = false
    override var baseColor: UIColorEnum = UIColorEnum.NEUTRAL_LIGHT
    override fun draw() {
        val tess = Tessellator.getInstance()
        val wr = tess.worldRenderer
        val lineWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH)
        GL11.glLineWidth(2.0f)
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(0.0, 0.5 * height - 1.0, 0.0).color(baseColor.r, baseColor.g, baseColor.b, baseColor.a).endVertex()
        wr.pos(width, 0.5 * height - 1.0, 0.0).color(baseColor.r, baseColor.g, baseColor.b, baseColor.a).endVertex()
        tess.draw()
        GL11.glLineWidth(4.0f)
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(width * position, 0.0, 0.0).color(baseColor.r, baseColor.g, baseColor.b, baseColor.a).endVertex()
        wr.pos(width * position, height, 0.0).color(baseColor.r, baseColor.g, baseColor.b, baseColor.a).endVertex()
        tess.draw()
        GL11.glLineWidth(lineWidth)
    }

    override fun applyLayoutPost() {
        width = max(width, 2.0 * padding)
        height = max(height, 2.0 * padding)
    }
}