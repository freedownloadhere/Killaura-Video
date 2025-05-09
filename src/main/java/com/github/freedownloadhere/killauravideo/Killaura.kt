package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.rendering.ColorEnum
import com.github.freedownloadhere.killauravideo.rendering.EntityTracker
import com.github.freedownloadhere.killauravideo.utils.*
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.network.play.client.C02PacketUseEntity
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class Killaura {
    private var toggled = false
    private val attackTimer = RandomTimer(70L, 40L, 0.1)
    val entityTracker = EntityTracker()

    fun isEnabled() = toggled

    fun toggle() {
        toggled = !toggled
    }

    fun update(attacker: EntityPlayerSP, world: WorldClient) {
        if(!toggled) return

        if(!attackTimer.hasFinished()) return

        entityTracker.resetTrackers()
        for(target in world.loadedEntityList) {
            if(!goodEntityCheck(attacker, target)) continue
            if(!distanceCheck(attacker, target)) continue
            if(!fovCheck(attacker, target)) continue
            if(!rayTraceCheck(attacker, target, world)) continue

            entityTracker.trackEntity(target, EntityTracker.TrackType.HITBOX_HIGHLIGHT, ColorEnum.RED)
            simulateAttack(attacker, target)

            attackTimer.reset()
            return
        }
    }

    private fun goodEntityCheck(attacker: EntityPlayerSP, target: Entity): Boolean {
        if(target !is EntityLivingBase) return false
        if(target == attacker) return false
        if(target.isDead) return false
        if(target.isInvisible) return false
        return true
    }

    private fun distanceCheck(attacker: EntityPlayerSP, target: Entity): Boolean {
        val attackerPos = attacker.positionVector
        val targetPos = target.positionVector
        val distance = attackerPos.distanceTo(targetPos)
        return (distance <= 4.0)
    }

    private fun rayTraceCheck(attacker: EntityPlayerSP, target: Entity, world: WorldClient): Boolean {
        val discardPastThis = 6.0

        val attackReach = 3.0
        val blockReach = 4.5

        val targetSamples = arrayOf(
            EntityPositions.head(target),
            // EntityPositions.torso(target),
            // EntityPositions.feet(target)
        )

        val attackerEyePosition = EntityPositions.head(attacker)

        for(sample in targetSamples) {
            val distance = attackerEyePosition.distanceTo(sample)
            if(distance > discardPastThis) continue

            val dir = (sample - attackerEyePosition).normalize()

            if(RayTrace.trace(attacker, dir, attackReach, blockReach, world) == target)
                return true
        }

        return false
    }

    private fun fovCheck(attacker: EntityPlayerSP, target: Entity): Boolean {
        val attackerVec = attacker.positionVector
        val entityVec = target.positionVector
        val deltaVec = entityVec.subtract(attackerVec)

        val posYaw = -atan2(deltaVec.xCoord, deltaVec.zCoord).toDegrees().toFloat().cropAngle180()
        val posPitch = -atan2(deltaVec.yCoord, hypot(deltaVec.xCoord, deltaVec.zCoord)).toDegrees().toFloat()

        val currentYaw = attacker.rotationYaw.cropAngle180()
        val currentPitch = attacker.rotationPitch

        val deltaYaw = (posYaw - currentYaw).cropAngle180()
        val deltaPitch = posPitch - currentPitch

        return abs(deltaYaw) <= 70.0f && abs(deltaPitch) <= 70.0f
    }

    private fun simulateAttack(attacker: EntityPlayerSP, target: Entity) {
        val packet = C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
        attacker.swingItem()
        attacker.sendQueue.addToSendQueue(packet)
    }
}