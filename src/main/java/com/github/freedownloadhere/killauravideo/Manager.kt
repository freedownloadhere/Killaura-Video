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

object Manager {
    private var killaura: Killaura? = null
    private var sprintReset: SprintReset? = null

    private var lastPlayer: EntityPlayerSP? = null
    private var lastWorld: WorldClient? = null

    fun toggleKillaura() {
        if(killaura == null) return
        killaura!!.toggle()
        val toggledMessage = if(killaura!!.isEnabled()) "\u00A7aEnabled" else "\u00A7cDisabled"
        Chat.addMessage("Killaura", toggledMessage)
    }

    @SubscribeEvent
    fun clientTick(e: TickEvent.ClientTickEvent) {
        if(e.phase != TickEvent.Phase.START)
            return

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld

        if(player != lastPlayer || world != lastWorld)
            onPlayerOrWorldChange(player, world)

        lastPlayer = player
        lastWorld = world

        if(player == null || world == null)
            return

        killaura?.update()
        sprintReset?.update()
    }

    @SubscribeEvent
    fun blockHighlight(e: DrawBlockHighlightEvent) {
        Timer.updateAllTimers()
        RandomTimer.updateAllTimers()

        val player = Minecraft.getMinecraft().thePlayer
        val world = Minecraft.getMinecraft().theWorld
        if(player == null || world == null) return

        RenderUtils.beginHighlight()
        RenderUtils.useAbsolutePos(EntityPositions.base(player))
        killaura?.render()
        RenderUtils.endHighlight()
    }

    @SubscribeEvent
    fun playerAttackEntity(e: AttackEntityEvent) {
        LogManager.getLogger().info("Attack entity")
        sprintReset?.stopSprint()
    }

    private fun onPlayerOrWorldChange(player: EntityPlayerSP?, world: WorldClient?) {
        if(player == null || world == null) {
            killaura = null
            sprintReset = null
            return
        }
        killaura = Killaura(player, world)
        sprintReset = SprintReset(player, Minecraft.getMinecraft().gameSettings.keyBindForward)
    }
}