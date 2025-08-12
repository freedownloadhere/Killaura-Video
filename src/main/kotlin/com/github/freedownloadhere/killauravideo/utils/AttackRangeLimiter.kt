package com.github.freedownloadhere.killauravideo.utils

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.rendering.ColorEnum
import com.github.freedownloadhere.killauravideo.rendering.RenderUtils
import com.github.freedownloadhere.killauravideo.utils.extensions.cropAngle180
import com.github.freedownloadhere.killauravideo.utils.extensions.toDegrees
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import kotlin.math.abs
import kotlin.math.atan2

class AttackRangeLimiter(var maxReach: Double = 3.0, var maxFov: Float = 30.0f): IRenderable
{
    private val player = Minecraft.getMinecraft().thePlayer

    fun isInRange(target: Entity): Boolean {
        return reachCheck(target) && fovCheck(target)
    }

    private fun reachCheck(target: Entity): Boolean {
        val attackerPos = player.positionVector
        val targetPos = target.positionVector
        val distance = attackerPos.distanceTo(targetPos)
        return (distance <= (maxReach + 1))
    }

    private fun fovCheck(target: Entity): Boolean {
        val attackerVec = player.positionVector
        val entityVec = target.positionVector
        val deltaVec = entityVec.subtract(attackerVec)

        val posYaw = -atan2(deltaVec.xCoord, deltaVec.zCoord).toDegrees().toFloat().cropAngle180()
//        val posPitch = -atan2(deltaVec.yCoord, hypot(deltaVec.xCoord, deltaVec.zCoord)).toDegrees().toFloat()

        val currentYaw = player.rotationYaw.cropAngle180()
//        val currentPitch = attacker.rotationPitch

        val deltaYaw = (posYaw - currentYaw).cropAngle180()
//        val deltaPitch = posPitch - currentPitch

        return abs(deltaYaw) <= maxFov // && abs(deltaPitch) <= 70.0f
    }

    override fun render() {
        RenderUtils.drawSlice(
            EntityPositions.base(player),
            maxReach,
            ColorEnum.RED.solid,
            maxFov,
            player.rotationYaw
        )
        RenderUtils.drawSlice(
            EntityPositions.base(player),
            maxReach,
            ColorEnum.RED.translucent,
            maxFov,
            player.rotationYaw,
            filledShape = true
        )
    }
}