package com.github.freedownloadhere.killauravideo

import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object Manager {
    private val killaura = Killaura()

    fun toggleKillaura() {
        killaura.toggle()
        val toggledMessage = if(killaura.isEnabled()) "\u00A7aEnabled" else "\u00A7cDisabled"
        Chat.addMessage("Killaura", toggledMessage)
    }

    @SubscribeEvent
    fun clientTick(e: TickEvent.ClientTickEvent) {
        if(e.phase != TickEvent.Phase.START)
            // we want to attack on the start of a tick
            return

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null)
            // checking if we are in a world
            return

        killaura.update(player, world)
    }
}