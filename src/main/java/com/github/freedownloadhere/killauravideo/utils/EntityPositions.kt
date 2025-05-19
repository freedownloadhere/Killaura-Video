package com.github.freedownloadhere.killauravideo.utils

import com.github.freedownloadhere.killauravideo.utils.extensions.plus
import net.minecraft.entity.Entity
import net.minecraft.util.Vec3

object EntityPositions {
    fun base(entity: Entity): Vec3 {
        val pticks = PartialTicks.asFloat
        return Vec3(
            entity.prevPosX + (entity.posX - entity.prevPosX) * pticks,
            entity.prevPosY + (entity.posY - entity.prevPosY) * pticks,
            entity.prevPosZ + (entity.posZ - entity.prevPosZ) * pticks
        )
    }

    fun feet(entity: Entity): Vec3 {
        return base(entity) + Vec3(0.0, entity.eyeHeight * 0.2, 0.0)
    }

    fun torso(entity: Entity): Vec3 {
        return base(entity) + Vec3(0.0, entity.eyeHeight * 0.6, 0.0)
    }

    fun head(entity: Entity): Vec3 {
        return base(entity) + Vec3(0.0, entity.eyeHeight * 1.0, 0.0)
    }
}