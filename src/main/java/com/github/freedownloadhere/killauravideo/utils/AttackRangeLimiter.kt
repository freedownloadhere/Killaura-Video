package com.github.freedownloadhere.killauravideo.utils

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.rendering.ColorEnum
import com.github.freedownloadhere.killauravideo.rendering.RenderUtils
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import kotlin.math.abs
import kotlin.math.atan2

class AttackRangeLimiter(
    private val attacker: EntityPlayerSP,
    private var maxReach: Double = 3.0,
    private var maxFov: Float = 30.0f
) : IRenderable {
    fun isInRange(target: Entity): Boolean {
        return reachCheck(target) && fovCheck(target)
    }

    private fun reachCheck(target: Entity): Boolean {
        val attackerPos = attacker.positionVector
        val targetPos = target.positionVector
        val distance = attackerPos.distanceTo(targetPos)
        return (distance <= (maxReach + 1))
    }

    private fun fovCheck(target: Entity): Boolean {
        val attackerVec = attacker.positionVector
        val entityVec = target.positionVector
        val deltaVec = entityVec.subtract(attackerVec)

        val posYaw = -atan2(deltaVec.xCoord, deltaVec.zCoord).toDegrees().toFloat().cropAngle180()
//        val posPitch = -atan2(deltaVec.yCoord, hypot(deltaVec.xCoord, deltaVec.zCoord)).toDegrees().toFloat()

        val currentYaw = attacker.rotationYaw.cropAngle180()
//        val currentPitch = attacker.rotationPitch

        val deltaYaw = (posYaw - currentYaw).cropAngle180()
//        val deltaPitch = posPitch - currentPitch

        return abs(deltaYaw) <= maxFov // && abs(deltaPitch) <= 70.0f
    }

    override fun render() {
        RenderUtils.drawSlice(
            EntityPositions.base(attacker),
            maxReach,
            ColorEnum.RED.solid,
            maxFov,
            attacker.rotationYaw
        )
        RenderUtils.drawSlice(
            EntityPositions.base(attacker),
            maxReach,
            ColorEnum.RED.translucent,
            maxFov,
            attacker.rotationYaw,
            filledShape = true
        )
    }
}