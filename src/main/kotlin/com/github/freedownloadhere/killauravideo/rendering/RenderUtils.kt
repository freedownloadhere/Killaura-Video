package com.github.freedownloadhere.killauravideo.rendering

import com.github.freedownloadhere.killauravideo.utils.extensions.toRadians
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object RenderUtils
{
    data class Index(val i1 : Int, val i2 : Int, val i3 : Int, val i4 : Int)

    private val tess = Tessellator.getInstance()
    private val wr = Tessellator.getInstance().worldRenderer
    private val cuboidVertices = arrayOf(
        Vec3(0.0, 0.0, 0.0),
        Vec3(1.0, 0.0, 0.0),
        Vec3(1.0, 0.0, 1.0),
        Vec3(0.0, 0.0, 1.0),
        Vec3(0.0, 1.0, 0.0),
        Vec3(1.0, 1.0, 0.0),
        Vec3(1.0, 1.0, 1.0),
        Vec3(0.0, 1.0, 1.0)
    )
    private val cuboidIndices = arrayOf(
        Index(0, 1, 2, 3),
        Index(4, 5, 6, 7),
        Index(0, 1, 5, 4),
        Index(1, 2, 6, 5),
        Index(2, 3, 7, 6),
        Index(3, 0, 4, 7)
    )
    private val cuboidOutlineVertices = arrayOf(
        Vec3(0.0, 0.0, 0.0),
        Vec3(1.0, 0.0, 0.0),

        Vec3(0.0, 0.0, 1.0),
        Vec3(1.0, 0.0, 1.0),

        Vec3(0.0, 0.0, 0.0),
        Vec3(0.0, 0.0, 1.0),

        Vec3(1.0, 0.0, 0.0),
        Vec3(1.0, 0.0, 1.0),

        Vec3(0.0, 0.0, 0.0),
        Vec3(0.0, 1.0, 0.0),

        Vec3(1.0, 0.0, 0.0),
        Vec3(1.0, 1.0, 0.0),

        Vec3(0.0, 0.0, 1.0),
        Vec3(0.0, 1.0, 1.0),

        Vec3(1.0, 0.0, 1.0),
        Vec3(1.0, 1.0, 1.0),

        Vec3(0.0, 1.0, 0.0),
        Vec3(1.0, 1.0, 0.0),

        Vec3(0.0, 1.0, 1.0),
        Vec3(1.0, 1.0, 1.0),

        Vec3(0.0, 1.0, 0.0),
        Vec3(0.0, 1.0, 1.0),

        Vec3(1.0, 1.0, 0.0),
        Vec3(1.0, 1.0, 1.0),
    )

    fun beginHighlight() {
        GlStateManager.pushMatrix()
        GlStateManager.disableLighting()
        GlStateManager.disableTexture2D()
        GlStateManager.depthFunc(GL11.GL_ALWAYS)
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    }

    fun useAbsolutePos(playerPos: Vec3) {
        GlStateManager.translate(-playerPos.xCoord, -playerPos.yCoord, -playerPos.zCoord)
    }

    fun endHighlight() {
        GlStateManager.depthFunc(GL11.GL_LEQUAL)
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()
        GlStateManager.popMatrix()
    }

    fun drawLine(
        pos1: Vec3,
        pos2: Vec3,
        color: Color
    ) {
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()
        wr.pos(pos2.xCoord, pos2.yCoord, pos2.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()
        tess.draw()
    }

    fun drawCuboid(
        minCorner: Vec3,
        lengths: Vec3,
        color: Color,
        filledShape: Boolean = false
    ) {
        val mode = if(filledShape) GL11.GL_QUADS else GL11.GL_LINES

        wr.begin(mode, DefaultVertexFormats.POSITION_COLOR)
        GlStateManager.pushMatrix()
        GlStateManager.translate(minCorner.xCoord, minCorner.yCoord, minCorner.zCoord)
        GlStateManager.scale(lengths.xCoord, lengths.yCoord, lengths.zCoord)

        if(!filledShape) for(vertex in cuboidOutlineVertices)
            wr.pos(vertex.xCoord, vertex.yCoord, vertex.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()

        else for(quad in cuboidIndices) {
            val p1 = cuboidVertices[quad.i1]
            val p2 = cuboidVertices[quad.i2]
            val p3 = cuboidVertices[quad.i3]
            val p4 = cuboidVertices[quad.i4]
            wr.pos(p1.xCoord, p1.yCoord, p1.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()
            wr.pos(p2.xCoord, p2.yCoord, p2.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()
            wr.pos(p3.xCoord, p3.yCoord, p3.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()
            wr.pos(p4.xCoord, p4.yCoord, p4.zCoord).color(color.red, color.green, color.blue, color.alpha).endVertex()
        }

        tess.draw()
        GlStateManager.popMatrix()
    }

    fun drawCircle(
        center: Vec3,
        radius: Double,
        color: Color,
        filledShape: Boolean = false
    ) {
        val mode = if(filledShape) GL11.GL_POLYGON else GL11.GL_LINE_LOOP

        wr.begin(mode, DefaultVertexFormats.POSITION_COLOR)
        GlStateManager.pushMatrix()
        GlStateManager.translate(center.xCoord, center.yCoord, center.zCoord)
        GlStateManager.scale(radius, 1.0, radius)

        val divisions = (20 * radius).toInt()

        for(i in 1 .. divisions) {
            val angleRad = (2.0 * PI * i) / divisions
            wr.pos(sin(angleRad), 0.0, cos(angleRad)).color(color.red, color.green, color.blue, color.alpha).endVertex()
        }

        tess.draw()
        GlStateManager.popMatrix()
    }

    fun drawSlice(
        center: Vec3,
        radius: Double,
        color: Color,
        maxDeg: Float,
        centerDeg: Float,
        filledShape: Boolean = false
    ) {
        val mode = if(filledShape) GL11.GL_POLYGON else GL11.GL_LINE_LOOP

        wr.begin(mode, DefaultVertexFormats.POSITION_COLOR)

        GlStateManager.pushMatrix()
        GlStateManager.translate(center.xCoord, center.yCoord + 1e-2, center.zCoord)
        GlStateManager.scale(radius, 1.0, radius)
        GlStateManager.rotate(-(centerDeg + maxDeg), 0.0f, 1.0f, 0.0f)

        val divisions = maxDeg.toInt() / 3
        val doubledMaxRad = 2.0 * maxDeg.toRadians()

        for(i in 0 .. divisions) {
            val angleRad = (doubledMaxRad * i) / divisions
            wr.pos(sin(angleRad), 0.0, cos(angleRad)).color(color.red, color.green, color.blue, color.alpha).endVertex()
        }
        wr.pos(0.0, 0.0, 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
//        wr.pos(0.0, 0.0, 1.0).color(color.red, color.green, color.blue, color.alpha).endVertex()

        tess.draw()
        GlStateManager.popMatrix()
    }
}