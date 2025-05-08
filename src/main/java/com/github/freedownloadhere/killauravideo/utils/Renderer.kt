package com.github.freedownloadhere.killauravideo.utils

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11

object Renderer {
    enum class Color(
        val r: Int,
        val g: Int,
        val b: Int,
        val a: Int
    ) {
        RED(255, 0, 0, 255),
        GREEN(0, 255, 0, 255),
        RED_FADED(255, 0, 0, 30),
        GREEN_FADED(0, 255, 0, 30),
    }

    data class Point(val coord: Vec3, val color: Color)
    data class Cuboid(val entity: Entity, val color: Color)
    data class Index(val i1 : Int, val i2 : Int, val i3 : Int, val i4 : Int)

    private val linesFromPlayer = mutableListOf<Point>()
    private val cuboids = mutableListOf<Cuboid>()

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

    fun addLineFromPlayer(to: Vec3, color: Color) {
        linesFromPlayer.add(Point(to, color))
    }

    fun addCuboid(entity: Entity, color: Color) {

        cuboids.add(Cuboid(entity, color))
    }

    fun resetLinesFromPlayer() {
        linesFromPlayer.clear()
    }

    fun resetCuboids() {
        cuboids.clear()
    }

    fun renderAll(tessellator: Tessellator, player: EntityPlayerSP) {
        GlStateManager.pushMatrix()
        GlStateManager.disableLighting()
        GlStateManager.disableTexture2D()
        GlStateManager.depthFunc(GL11.GL_ALWAYS)
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        val ppos = EntityPositions.base(player)
        GlStateManager.translate(-ppos.xCoord, -ppos.yCoord, -ppos.zCoord)

        renderLinesFromPlayer(tessellator, player)
        renderCubes(tessellator)

        GlStateManager.depthFunc(GL11.GL_LEQUAL)
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()
        GlStateManager.popMatrix()
    }

    private fun renderCubes(tessellator: Tessellator) {
        val wr = tessellator.worldRenderer

        for(cuboid in cuboids) {
            wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
            val aabb = cuboid.entity.entityBoundingBox
            val scale = Vec3(aabb.maxX - aabb.minX, aabb.maxY - aabb.minY, aabb.maxZ - aabb.minZ)
            val diff = Vec3((aabb.maxX - aabb.minX) * 0.5, 0.0, (aabb.maxZ - aabb.minZ) * 0.5)
            val corner = EntityPositions.base(cuboid.entity) - diff
            GlStateManager.pushMatrix()
            GlStateManager.translate(corner.xCoord, corner.yCoord, corner.zCoord)
            GlStateManager.scale(scale.xCoord, scale.yCoord, scale.zCoord)
            for(quad in cuboidIndices) {
                val p1 = cuboidVertices[quad.i1]
                val p2 = cuboidVertices[quad.i2]
                val p3 = cuboidVertices[quad.i3]
                val p4 = cuboidVertices[quad.i4]
                wr.pos(p1.xCoord, p1.yCoord, p1.zCoord).color(cuboid.color.r, cuboid.color.g, cuboid.color.b, cuboid.color.a).endVertex()
                wr.pos(p2.xCoord, p2.yCoord, p2.zCoord).color(cuboid.color.r, cuboid.color.g, cuboid.color.b, cuboid.color.a).endVertex()
                wr.pos(p3.xCoord, p3.yCoord, p3.zCoord).color(cuboid.color.r, cuboid.color.g, cuboid.color.b, cuboid.color.a).endVertex()
                wr.pos(p4.xCoord, p4.yCoord, p4.zCoord).color(cuboid.color.r, cuboid.color.g, cuboid.color.b, cuboid.color.a).endVertex()
            }
            tessellator.draw()
            GlStateManager.popMatrix()
        }
    }

    private fun renderLinesFromPlayer(tessellator: Tessellator, player: EntityPlayerSP) {
        val wr = tessellator.worldRenderer

        val pos1 = EntityPositions.head(player)

        for(pt in linesFromPlayer) {
            wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
            wr.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).color(pt.color.r, pt.color.g, pt.color.b, pt.color.a).endVertex()
            wr.pos(pt.coord.xCoord, pt.coord.yCoord, pt.coord.zCoord).color(pt.color.r, pt.color.g, pt.color.b, pt.color.a).endVertex()
            tessellator.draw()
        }
    }
}