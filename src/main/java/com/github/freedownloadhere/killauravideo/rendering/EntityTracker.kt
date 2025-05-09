package com.github.freedownloadhere.killauravideo.rendering

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.utils.EntityPositions
import com.github.freedownloadhere.killauravideo.utils.minus
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.entity.Entity
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11

class EntityTracker(
    private val playerForTracerRendering: EntityPlayerSP
) : IRenderable {
    enum class TrackType {
        HITBOX_HIGHLIGHT,
        TRACER
    }

    inner class TrackData(
        private val entity: Entity,
        private val type: TrackType,
        private val color: ColorEnum
    ) : IRenderable {
        override fun render() {
            when(type) {
                TrackType.HITBOX_HIGHLIGHT -> asHitboxHighlight()
                TrackType.TRACER -> asTracer()
            }
        }

        private fun asHitboxHighlight() {
            val cbSize = entity.collisionBorderSize.toDouble()
            val aabb = entity.entityBoundingBox.expand(cbSize, cbSize, cbSize)
            val lengths = Vec3(aabb.maxX - aabb.minX, aabb.maxY - aabb.minY, aabb.maxZ - aabb.minZ)
            val diff = Vec3((aabb.maxX - aabb.minX) * 0.5, 0.0, (aabb.maxZ - aabb.minZ) * 0.5)
            val minCorner = EntityPositions.base(entity) - diff
            RenderUtils.drawCuboid(minCorner, lengths, color.solid)
            RenderUtils.drawCuboid(minCorner, lengths, color.translucent, filledShape = true)
        }

        private fun asTracer() {
            val pos1 = EntityPositions.head(playerForTracerRendering)
            val pos2 = EntityPositions.head(entity)
            RenderUtils.drawLine(pos1, pos2, color.solid)
        }
    }

    private val trackList = mutableListOf<TrackData>()

    fun trackEntity(entity: Entity, trackType: TrackType, trackColor: ColorEnum = ColorEnum.RED) {
        trackList.add(TrackData(entity, trackType, trackColor))
    }

    fun resetTrackers() {
        trackList.clear()
    }

    override fun render() {
        for(tracker in trackList)
            tracker.render()
    }
}