package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.rendering.ColorEnum
import com.github.freedownloadhere.killauravideo.rendering.EntityTracker
import com.github.freedownloadhere.killauravideo.rendering.Renderer
import com.github.freedownloadhere.killauravideo.utils.*
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.event.world.WorldEvent
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
        if(e.phase != TickEvent.Phase.START) return

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        RandomTimer.updateAllTimers()
        killaura.update(player, world)
    }

    @SubscribeEvent
    fun blockHighlight(e: DrawBlockHighlightEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        val tess = Tessellator.getInstance()
        killaura.entityTracker.playerPosForRendering = EntityPositions.head(player)

        Renderer.beginHighlight(EntityPositions.base(player))
        Renderer.drawSlice(
            EntityPositions.base(player),
            3.0,
            ColorEnum.RED_FADED,
            70.0,
            player.rotationYaw,
            tess
        )
        killaura.entityTracker.render(tess)
        Renderer.endHighlight()
    }

    @SubscribeEvent
    fun worldUnload(e: WorldEvent.Unload) {
        killaura.entityTracker.resetTrackers()
    }
}