package com.github.freedownloadhere.killauravideo.modules

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.rendering.ColorEnum
import com.github.freedownloadhere.killauravideo.rendering.EntityTracker
import com.github.freedownloadhere.killauravideo.utils.*
import com.github.freedownloadhere.killauravideo.utils.timer.TimerManager
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.network.play.client.C02PacketUseEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.AttackEntityEvent

class Killaura(
    private val attacker: EntityPlayerSP,
    private val world: WorldClient,
    timerManager: TimerManager
) : Module("Killaura"), IRenderable
{
    private var attackReach = 3.0
    private val attackTimer = timerManager.newRandomTimer(70L, 40L, 0.1)
    private val attackRangeLimiter = AttackRangeLimiter(attacker, maxReach = attackReach)
    private val entityTracker = EntityTracker(attacker)

    override fun update() {
        if(!toggleSwitch.isOn) return
        if(!attackTimer.hasFinished()) return

        entityTracker.resetTrackers()

        for(target in world.loadedEntityList) {
            if(!goodEntityCheck(target)) continue
            if(!attackRangeLimiter.isInRange(target)) continue
            if(!rayTraceCheck(target)) continue

            entityTracker.trackEntity(target, EntityTracker.TrackType.HITBOX_HIGHLIGHT, ColorEnum.GREEN)
            simulateAttack(target)

            attackTimer.reset()
            return
        }
    }

    private fun goodEntityCheck(target: Entity): Boolean {
        if(target !is EntityLivingBase) return false
        if(target == attacker) return false
        if(target.isDead) return false
        if(target.isInvisible) return false
        return true
    }

    private fun rayTraceCheck(target: Entity): Boolean {
        val discardPastThis = 6.0

        val blockReach = 1.5 * attackReach

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

    private fun simulateAttack(target: Entity) {
        val packet = C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
        attacker.swingItem()
        attacker.sendQueue.addToSendQueue(packet)
        MinecraftForge.EVENT_BUS.post(AttackEntityEvent(attacker, target))
    }

    override fun render() {
        if(!toggleSwitch.isOn) return

        entityTracker.render()
        attackRangeLimiter.render()
    }
}