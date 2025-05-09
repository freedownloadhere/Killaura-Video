package com.github.freedownloadhere.killauravideo.rendering

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.utils.EntityPositions
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11

object Renderer {
    data class Point(val coord: Vec3, val color: ColorEnum)
    data class Index(val i1 : Int, val i2 : Int, val i3 : Int, val i4 : Int)

    private val linesFromPlayer = mutableListOf<Point>()

    val cuboidVertices = arrayOf(
        Vec3(0.0, 0.0, 0.0),
        Vec3(1.0, 0.0, 0.0),
        Vec3(1.0, 0.0, 1.0),
        Vec3(0.0, 0.0, 1.0),
        Vec3(0.0, 1.0, 0.0),
        Vec3(1.0, 1.0, 0.0),
        Vec3(1.0, 1.0, 1.0),
        Vec3(0.0, 1.0, 1.0)
    )

    val cuboidIndices = arrayOf(
        Index(0, 1, 2, 3),
        Index(4, 5, 6, 7),
        Index(0, 1, 5, 4),
        Index(1, 2, 6, 5),
        Index(2, 3, 7, 6),
        Index(3, 0, 4, 7)
    )

    fun beginHighlight(playerPos: Vec3) {
        GlStateManager.pushMatrix()
        GlStateManager.disableLighting()
        GlStateManager.disableTexture2D()
        GlStateManager.depthFunc(GL11.GL_ALWAYS)
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GlStateManager.translate(-playerPos.xCoord, -playerPos.yCoord, -playerPos.zCoord)
    }

    fun endHighlight() {
        GlStateManager.depthFunc(GL11.GL_LEQUAL)
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()
        GlStateManager.popMatrix()
    }

    fun render(renderable: IRenderable, tess: Tessellator) {
        renderable.render(tess)
    }

    fun addLineFromPlayer(to: Vec3, color: ColorEnum) {
        linesFromPlayer.add(Point(to, color))
    }

    fun resetLinesFromPlayer() {
        linesFromPlayer.clear()
    }

    private fun renderAllLinesFromPlayer(tessellator: Tessellator, player: EntityPlayerSP) {
        val wr = tessellator.worldRenderer
        val pos1 = EntityPositions.head(player)
        for(pt in linesFromPlayer)
            renderLineFromPlayer(pos1, pt, wr, tessellator)
    }

    private fun renderLineFromPlayer(pos1: Vec3, pt: Point, wr: WorldRenderer, tessellator: Tessellator) {
        wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
        wr.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).color(pt.color.r, pt.color.g, pt.color.b, pt.color.a).endVertex()
        wr.pos(pt.coord.xCoord, pt.coord.yCoord, pt.coord.zCoord).color(pt.color.r, pt.color.g, pt.color.b, pt.color.a).endVertex()
        tessellator.draw()
    }
}