package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.utils.*
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object Manager {
    private val killaura = Killaura()
    private val entityTracker = EntityTracker()

    fun toggleKillaura() {
        killaura.toggle()
        val toggledMessage = if(killaura.isEnabled()) "\u00A7aEnabled" else "\u00A7cDisabled"
        Chat.addMessage("Killaura", toggledMessage)
    }

    @SubscribeEvent
    fun clientTick(e: TickEvent.ClientTickEvent) {
        if(e.phase != TickEvent.Phase.START) return

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        RandomTimer.updateAllTimers()
        killaura.update(player, world, entityTracker)
    }

    @SubscribeEvent
    fun blockHighlight(e: DrawBlockHighlightEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        val tess = Tessellator.getInstance()
        entityTracker.playerPosForRendering = EntityPositions.head(player)

        Renderer.beginHighlight(EntityPositions.base(player))
        Renderer.render(entityTracker, tess)
        Renderer.endHighlight()
    }

    @SubscribeEvent
    fun worldUnload(e: WorldEvent.Unload) {
        entityTracker.resetTrackers()
    }
}