package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.rendering.RenderUtils
import com.github.freedownloadhere.killauravideo.utils.*
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.apache.logging.log4j.LogManager

object GlobalManager {
    private var clientInstance: ClientInstance? = null

    private var lastPlayer: EntityPlayerSP? = null
    private var lastWorld: WorldClient? = null

    fun toggleModule(name: String) {
        clientInstance?.toggleModule(name)
    }

    @SubscribeEvent
    fun clientTick(e: TickEvent.ClientTickEvent) {
        if(e.phase != TickEvent.Phase.START)
            return

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld

        if(player != lastPlayer || world != lastWorld)
            contextChange(player, world)

        lastPlayer = player
        lastWorld = world

        clientInstance?.tickUpdate()
    }

    @SubscribeEvent
    fun blockHighlight(e: DrawBlockHighlightEvent) {
        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        RenderUtils.beginHighlight()
        RenderUtils.useAbsolutePos(EntityPositions.base(player))
        clientInstance?.renderUpdate()
        RenderUtils.endHighlight()
    }

    // TODO fix
    @SubscribeEvent
    fun playerAttackEntity(e: AttackEntityEvent) {
        // sprintReset?.stopSprint()
    }

    private fun contextChange(player: EntityPlayerSP?, world: WorldClient?) {
        if(player == null || world == null) {
            clientInstance = null
            return
        }
        clientInstance = ClientInstance(player, world, Minecraft.getMinecraft().gameSettings)
    }
}