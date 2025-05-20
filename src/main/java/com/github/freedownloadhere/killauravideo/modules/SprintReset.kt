package com.github.freedownloadhere.killauravideo.modules

import com.github.freedownloadhere.killauravideo.utils.timer.TimerManager
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class SprintReset(
    private val player: EntityPlayerSP,
    private val forwardKey: KeyBinding,
    timerManager: TimerManager
) : Module("SprintReset")
{
    private var breakingSprint = false
    private val timer = timerManager.newTimer(100L)

    override fun init() {
        MinecraftForge.EVENT_BUS.register(this)
    }

    override fun destroy() {
        MinecraftForge.EVENT_BUS.unregister(this)
    }

    override fun update() {
        if(!toggled) return

        if(breakingSprint) {
            KeyBinding.setKeyBindState(forwardKey.keyCode, false)
            if(timer.hasFinished()) {
                breakingSprint = false
                KeyBinding.setKeyBindState(forwardKey.keyCode, true)
                player.isSprinting = true
            }
            return
        }

        if(forwardKey.isPressed)
            player.isSprinting = true
    }

    @SubscribeEvent
    fun stopSprint(e: AttackEntityEvent) {
        if(!timer.hasFinished()) return
        if(!player.isSprinting) return

        timer.reset()
        player.isSprinting = false
        breakingSprint = true
    }
}