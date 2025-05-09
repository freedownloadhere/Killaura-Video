package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.utils.Timer
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.settings.KeyBinding
import org.apache.logging.log4j.LogManager

class SprintReset(
    private val player: EntityPlayerSP,
    private val forwardKey: KeyBinding
) {
    private var breakingSprint = false
    private val timer = Timer(50L)

    fun update() {
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

    fun stopSprint() {
        LogManager.getLogger().info("Breaking sprint")

        if(!timer.hasFinished()) return
        LogManager.getLogger().info("Time has finished")

        if(!player.isSprinting) return
        LogManager.getLogger().info("Forward key")

        timer.reset()
        player.isSprinting = false
        breakingSprint = true
    }
}