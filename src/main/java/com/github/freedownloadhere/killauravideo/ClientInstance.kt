package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.interfaces.IDestructible
import com.github.freedownloadhere.killauravideo.interfaces.IInitializable
import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.modules.Killaura
import com.github.freedownloadhere.killauravideo.modules.ModuleMap
import com.github.freedownloadhere.killauravideo.modules.SprintReset
import com.github.freedownloadhere.killauravideo.utils.Chat
import com.github.freedownloadhere.killauravideo.utils.timer.TimerManager
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.settings.GameSettings

class ClientInstance(
    player: EntityPlayerSP,
    world: WorldClient,
    gameSettings: GameSettings
) : IInitializable, IDestructible
{
    private val timerManager = TimerManager()

    val moduleMap = ModuleMap(
        Killaura(player, world, timerManager),
        SprintReset(player, gameSettings.keyBindForward, timerManager)
    )

    fun toggleModule(name: String) {
        val module = moduleMap.module(name)
        if(module == null) {
            Chat.addMessage("Error", "No such module \"$name\" exists")
            return
        }
        module.toggle()
        val toggledMessage = if(module.isEnabled()) "\u00A7aEnabled" else "\u00A7cDisabled"
        Chat.addMessage(module.name, toggledMessage)
    }

    fun tickUpdate() {
        for(module in moduleMap.allModules())
            module.update()
    }

    fun renderUpdate() {
        timerManager.update()
        for(module in moduleMap.allModules())
            if(module is IRenderable)
                module.render()
    }

    override fun init() {
        moduleMap.init()
    }

    override fun destroy() {
        moduleMap.destroy()
    }
}