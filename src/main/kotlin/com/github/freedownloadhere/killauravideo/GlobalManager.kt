package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.rendering.RenderUtils
import com.github.freedownloadhere.killauravideo.utils.EntityPositions
import com.github.freedownloadhere.killauravideo.utils.KeybindMap
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object GlobalManager
{
    var client: Client? = null
        private set

    private var lastPlayer: EntityPlayerSP? = null
    private var lastWorld: WorldClient? = null

    @SubscribeEvent
    fun clientTick(e: TickEvent.ClientTickEvent) {
        if(e.phase != TickEvent.Phase.START)
            return

        KeybindMap.update()

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld

        if(player != lastPlayer || world != lastWorld)
            contextChange(player, world)

        lastPlayer = player
        lastWorld = world

        client?.tickUpdate()
    }

    @SubscribeEvent
    fun blockHighlight(e: DrawBlockHighlightEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        RenderUtils.beginHighlight()
        RenderUtils.useAbsolutePos(EntityPositions.base(player))
        client?.renderUpdate()
        RenderUtils.endHighlight()
    }

    private fun contextChange(player: EntityPlayerSP?, world: WorldClient?) {
        if(player == null || world == null) {
            client?.destroy()
            client = null
            return
        }
        client = Client()
        client!!.init()
    }
}