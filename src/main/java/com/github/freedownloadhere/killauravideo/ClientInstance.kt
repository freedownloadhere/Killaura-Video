package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.modules.Killaura
import com.github.freedownloadhere.killauravideo.modules.ModuleMap
import com.github.freedownloadhere.killauravideo.modules.SprintReset
import com.github.freedownloadhere.killauravideo.utils.Chat
import com.github.freedownloadhere.killauravideo.utils.timer.TimerManager
import com.lukflug.panelstudio.setting.ICategory
import com.lukflug.panelstudio.setting.IClient
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.settings.GameSettings
import java.util.stream.Stream

class ClientInstance(
    player: EntityPlayerSP,
    world: WorldClient,
    gameSettings: GameSettings
) : IClient
{
    private val timerManager = TimerManager()

    val moduleMap = ModuleMap(
        "Combat" to listOf(
            Killaura(player, world, timerManager),
            SprintReset(player, gameSettings.keyBindForward, timerManager)
        )
    )

    fun init() {
        for(module in moduleMap.allModules())
            module.init()
    }

    fun destroy() {
        for(module in moduleMap.allModules())
            module.destroy()
    }

    fun toggleModule(name: String) {
        val module = moduleMap.module(name)
        if(module == null) {
            Chat.addMessage("Error", "No such module \"$name\" exists")
            return
        }
        module.toggleSwitch.toggle()
        val toggledMessage = if(module.toggleSwitch.isOn) "\u00A7aEnabled" else "\u00A7cDisabled"
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

    override fun getCategories(): Stream<ICategory> = moduleMap.allCategories().stream()
}