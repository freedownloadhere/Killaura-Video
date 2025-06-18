package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.rendering.RenderUtils
import com.github.freedownloadhere.killauravideo.ui.core.UICore
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.utils.EntityPositions
import com.github.freedownloadhere.killauravideo.utils.KeybindMap
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object GlobalManager {
    var clientInstance: ClientInstance? = null
        private set
    var core: UICore? = null

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

    private fun contextChange(player: EntityPlayerSP?, world: WorldClient?) {
        if(player == null || world == null) {
            clientInstance?.destroy()
            clientInstance = null
            return
        }
        clientInstance = ClientInstance(player, world, Minecraft.getMinecraft().gameSettings)
        clientInstance!!.init()
    }

    private var tempInit = false
    @SubscribeEvent
    fun renderWorldEvent(e: RenderWorldLastEvent) {
        if(tempInit) return
        val sw = Minecraft.getMinecraft().displayWidth.toFloat()
        val sh = Minecraft.getMinecraft().displayHeight.toFloat()
        RenderingBackend.init(sw, sh)
        RenderingBackend.loadTexture(
            "checkmark",
            "/assets/killauravideo/textures/gui/check.png"
        )
        tempInit = true
    }
}