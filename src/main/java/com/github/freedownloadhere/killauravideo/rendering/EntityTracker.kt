package com.github.freedownloadhere.killauravideo.rendering

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.utils.EntityPositions
import com.github.freedownloadhere.killauravideo.utils.minus
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11

class EntityTracker : IRenderable {
    enum class TrackType {
        HITBOX_HIGHLIGHT,
        TRACER
    }

    inner class TrackData(
        private val entity: Entity,
        private val type: TrackType,
        private val color: ColorEnum
    ) : IRenderable {
        override fun render(tess: Tessellator) {
            when(type) {
                TrackType.HITBOX_HIGHLIGHT -> asHitboxHighlight(tess)
                TrackType.TRACER -> asTracer(tess)
            }
        }

        private fun asHitboxHighlight(tess: Tessellator) {
            val wr = tess.worldRenderer

            val aabb = entity.entityBoundingBox
            val scale = Vec3(aabb.maxX - aabb.minX, aabb.maxY - aabb.minY, aabb.maxZ - aabb.minZ)
            val diff = Vec3((aabb.maxX - aabb.minX) * 0.5, 0.0, (aabb.maxZ - aabb.minZ) * 0.5)
            val corner = EntityPositions.base(entity) - diff

            wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
            GlStateManager.pushMatrix()
            GlStateManager.translate(corner.xCoord, corner.yCoord, corner.zCoord)
            GlStateManager.scale(scale.xCoord, scale.yCoord, scale.zCoord)
            for(quad in Renderer.cuboidIndices) {
                val p1 = Renderer.cuboidVertices[quad.i1]
                val p2 = Renderer.cuboidVertices[quad.i2]
                val p3 = Renderer.cuboidVertices[quad.i3]
                val p4 = Renderer.cuboidVertices[quad.i4]
                wr.pos(p1.xCoord, p1.yCoord, p1.zCoord).color(color.r, color.g, color.b, color.a).endVertex()
                wr.pos(p2.xCoord, p2.yCoord, p2.zCoord).color(color.r, color.g, color.b, color.a).endVertex()
                wr.pos(p3.xCoord, p3.yCoord, p3.zCoord).color(color.r, color.g, color.b, color.a).endVertex()
                wr.pos(p4.xCoord, p4.yCoord, p4.zCoord).color(color.r, color.g, color.b, color.a).endVertex()
            }
            tess.draw()
            GlStateManager.popMatrix()
        }

        private fun asTracer(tess: Tessellator) {
            val wr = tess.worldRenderer

            val pp = this@EntityTracker.playerPosForRendering
            val ep = EntityPositions.head(entity)

            wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR)
            wr.pos(pp.xCoord, pp.yCoord, pp.zCoord).color(color.r, color.g, color.b, color.a).endVertex()
            wr.pos(ep.xCoord, ep.yCoord, ep.zCoord).color(color.r, color.g, color.b, color.a).endVertex()
            tess.draw()
        }
    }

    var playerPosForRendering = Vec3(0.0, 0.0, 0.0)
    private val trackList = mutableListOf<TrackData>()

    fun trackEntity(entity: Entity, trackType: TrackType, trackColor: ColorEnum = ColorEnum.RED_FADED) {
        trackList.add(TrackData(entity, trackType, trackColor))
    }

    fun resetTrackers() {
        trackList.clear()
    }

    override fun render(tess: Tessellator) {
        for(tracker in trackList)
            tracker.render(tess)
    }
}