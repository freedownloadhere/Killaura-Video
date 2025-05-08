package com.github.freedownloadhere.killauravideo

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity
import net.minecraft.util.EntitySelectors
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3

object RayTrace {
    fun trace(
        player: EntityPlayerSP,
        look: Vec3,
        baseReach: Double,
        world: WorldClient
    ): Entity? {
        var hitEntity: Entity? = null

        val eyeBegin = player.getPositionEyes(PartialTicks.asFloat)

        val blockRay = traceBlocks(eyeBegin, look, baseReach, world)
        val reach = if(blockRay == null) baseReach
                    else eyeBegin.distanceTo(blockRay.hitVec)

        val eyeEnd = eyeBegin.addVector(
            look.xCoord * reach,
            look.yCoord * reach,
            look.zCoord * reach
        )

        val aabbToSearch = player.entityBoundingBox
            .addCoord(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach)
            .expand(1.0, 1.0, 1.0)

        val entityList = world.getEntitiesInAABBexcluding(
            player,
            aabbToSearch,
            fullPredicate
        )

        var closestHit = reach

        for(entity in entityList) {
            val cbSize = entity.collisionBorderSize.toDouble()
            val aabb = entity.entityBoundingBox.expand(cbSize, cbSize, cbSize)
            val intercept = aabb.calculateIntercept(eyeBegin, eyeEnd)

            if(aabb.isVecInside(eyeBegin)) {
                hitEntity = entity
                closestHit = 0.0
            }

            else if(intercept != null) {
                val hitDistance = eyeBegin.distanceTo(intercept.hitVec)
                if(hitDistance < closestHit || closestHit == 0.0) {
                    closestHit = hitDistance
                    hitEntity = entity
                }
            }
        }

        return hitEntity
    }

    @Suppress("RedundantSamConstructor", "RemoveExplicitTypeArguments")
    private val fullPredicate = Predicates.and(
        EntitySelectors.NOT_SPECTATING,
        Predicate<Entity> { input: Entity? -> input?.canBeCollidedWith() ?: false }
    )

    private fun traceBlocks(
        start: Vec3,
        look: Vec3,
        reach: Double,
        world: WorldClient
    ): MovingObjectPosition? {
        val end = start.addVector(
            look.xCoord * reach,
            look.yCoord * reach,
            look.zCoord * reach
        )
        return world.rayTraceBlocks(start, end, false, false, true)
    }
}