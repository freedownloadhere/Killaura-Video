package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.utils.Chat
import com.github.freedownloadhere.killauravideo.utils.RandomTimer
import com.github.freedownloadhere.killauravideo.utils.Renderer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraftforge.client.event.DrawBlockHighlightEvent
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

        RandomTimer.updateAllTimers()
        killaura.update(player, world)
    }

    @SubscribeEvent
    fun blockHighlight(e: DrawBlockHighlightEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val tess = Tessellator.getInstance()
        if(player == null) return

        Renderer.renderAll(tess, player)
    }
}