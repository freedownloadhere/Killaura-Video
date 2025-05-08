package com.github.freedownloadhere.killauravideo

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
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
        GREEN(0, 255, 0, 255);

        val packedColor : Int
            get() = (r shl 24) + (g shl 16) + (b shl 8) + a
    }

    data class Point(val coord: Vec3, val color: Color)

    private val linesFromPlayer = mutableListOf<Point>()

    fun addLineFromPlayer(to: Vec3, color: Color) {
        linesFromPlayer.add(Point(to, color))
    }

    fun resetLinesFromPlayer() {
        linesFromPlayer.clear()
    }

    fun renderAll(tessellator: Tessellator, player: EntityPlayerSP) {
        GlStateManager.pushMatrix()
        GlStateManager.disableLighting()
        GlStateManager.disableTexture2D()
        GlStateManager.depthFunc(GL11.GL_ALWAYS)
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        val pticks = PartialTicks.asFloat
        val ppos = Vec3(
            player.prevPosX + (player.posX - player.prevPosX) * pticks,
            player.prevPosY + (player.posY - player.prevPosY) * pticks,
            player.prevPosZ + (player.posZ - player.prevPosZ) * pticks
        )
        GlStateManager.translate(-ppos.xCoord, -ppos.yCoord, -ppos.zCoord)

        renderLinesFromPlayer(tessellator, player)

        GlStateManager.depthFunc(GL11.GL_LEQUAL)
        GlStateManager.enableTexture2D()
        GlStateManager.enableLighting()
        GlStateManager.popMatrix()
    }

    private fun renderLinesFromPlayer(tessellator: Tessellator, player: EntityPlayerSP) {
        val wr = tessellator.worldRenderer

        val pos1 = player.getPositionEyes(PartialTicks.asFloat)

        for(pt in linesFromPlayer) {
            wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
            wr.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).color(pt.color.r, pt.color.g, pt.color.b, pt.color.a).endVertex()
            wr.pos(pt.coord.xCoord, pt.coord.yCoord, pt.coord.zCoord).color(pt.color.r, pt.color.g, pt.color.b, pt.color.a).endVertex()
            tessellator.draw()
        }
    }
}