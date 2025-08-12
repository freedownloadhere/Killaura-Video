package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.interfaces.IDestructible
import com.github.freedownloadhere.killauravideo.interfaces.IInitializable
import com.github.freedownloadhere.killauravideo.interfaces.IRenderable
import com.github.freedownloadhere.killauravideo.modules.FakeBlocks
import com.github.freedownloadhere.killauravideo.modules.Killaura
import com.github.freedownloadhere.killauravideo.utils.ModuleMap
import com.github.freedownloadhere.killauravideo.modules.SprintReset
import com.github.freedownloadhere.killauravideo.utils.Chat
import com.github.freedownloadhere.killauravideo.utils.timer.TimerManager

class Client: IInitializable, IDestructible
{
    private val timerManager = TimerManager()

    val moduleMap = ModuleMap(
        Killaura(timerManager),
        SprintReset(timerManager),
        FakeBlocks()
    )

    fun toggleModule(name: String) {
        val module = moduleMap.module(name)
        if(module == null) {
            Chat.info("Error", "No such module \"$name\" exists")
            return
        }
        module.toggle()
        val toggledMessage = if(module.isEnabled()) "\u00A7aEnabled" else "\u00A7cDisabled"
        Chat.info(module.name, toggledMessage)
    }

    fun tickUpdate() {
        for(module in moduleMap.allModules())
            if(module.toggled)
                module.update()
    }

    fun renderUpdate() {
        timerManager.update()
        for(module in moduleMap.allModules())
            if(module.toggled && module is IRenderable)
                module.render()
    }

    override fun init() {
        moduleMap.init()
    }

    override fun destroy() {
        moduleMap.destroy()
    }
}