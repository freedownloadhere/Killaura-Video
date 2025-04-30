package com.github.freedownloadhere.killauravideo

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.network.play.client.C02PacketUseEntity

class Killaura {
    private var toggled = false

    fun isEnabled() = toggled

    fun toggle() {
        toggled = !toggled
    }

    fun update(attacker: EntityPlayerSP, world: WorldClient) {
        if(!toggled) return

        for(target in world.loadedEntityList) {
            if(!goodEntityCheck(attacker, target)) continue
            if(!distanceCheck(attacker, target)) continue

            simulateAttack(attacker, target)
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

    private fun simulateAttack(attacker: EntityPlayerSP, target: Entity) {
        val packet = C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
        attacker.swingItem() // works without, but WILL flag instantly
        attacker.sendQueue.addToSendQueue(packet)
    }
}