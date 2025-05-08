package com.github.freedownloadhere.killauravideo

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.network.play.client.C02PacketUseEntity
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3

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
        return (distance <= 3.5)
    }

    private fun visibleAndReachableCheck(attacker: EntityPlayerSP, target: Entity, world: WorldClient): Boolean {
        val aabb = target.entityBoundingBox.expand(-0.1, -0.1, -0.1)
        val reach = 4.0

        val samples = arrayOf(
            target.getPositionEyes(PartialTicks.asFloat),
        )

        val attackerEyePosition = attacker.getPositionEyes(PartialTicks.asFloat)

        var found = false
        Renderer.resetLinesFromPlayer()

        for(sample in samples) {
            val distance = attackerEyePosition.distanceTo(sample)
            if(distance > reach) continue

            val dir = (sample - attackerEyePosition).normalize()

            if(RayTrace.trace(attacker, dir, reach, world) == target) {
                found = true
                Renderer.addLineFromPlayer(sample, Renderer.Color.GREEN)
            }
            else {
                Renderer.addLineFromPlayer(sample, Renderer.Color.RED)
            }
        }

        return found
    }

    private fun simulateAttack(attacker: EntityPlayerSP, target: Entity) {
        val packet = C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
        attacker.swingItem() // works without, but WILL flag instantly
        attacker.sendQueue.addToSendQueue(packet)
    }
}