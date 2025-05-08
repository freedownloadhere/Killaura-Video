package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.utils.*
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.network.play.client.C02PacketUseEntity

class Killaura {
    private var toggled = false
    private val attackTimer = RandomTimer(70L, 40L, 0.1)

    fun isEnabled() = toggled

    fun toggle() {
        toggled = !toggled
    }

    fun update(attacker: EntityPlayerSP, world: WorldClient) {
        if(!toggled) return

        if(!attackTimer.hasFinished()) return

        for(target in world.loadedEntityList) {
            if(!goodEntityCheck(attacker, target)) continue
            if(!distanceCheck(attacker, target)) continue
            if(!visibleAndReachableCheck(attacker, target, world)) continue

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

    private fun visibleAndReachableCheck(attacker: EntityPlayerSP, target: Entity, world: WorldClient): Boolean {
        val discardPastThis = 6.0

        val attackReach = 3.0
        val blockReach = 4.5

        val targetSamples = arrayOf(
            EntityPositions.head(target),
            // EntityPositions.torso(target),
            // EntityPositions.feet(target)
        )

        val attackerEyePosition = EntityPositions.head(attacker)

        Renderer.resetLinesFromPlayer()
        Renderer.resetCuboids()

        for(sample in targetSamples) {
            val distance = attackerEyePosition.distanceTo(sample)
            if(distance > discardPastThis) continue

            val dir = (sample - attackerEyePosition).normalize()

            if(RayTrace.trace(attacker, dir, attackReach, blockReach, world) == target) {
                Renderer.addCuboid(target, Renderer.Color.GREEN_FADED)
                return true
            }
        }

        return false
    }

    private fun simulateAttack(attacker: EntityPlayerSP, target: Entity) {
        val packet = C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
        attacker.swingItem()
        attacker.sendQueue.addToSendQueue(packet)
    }
}