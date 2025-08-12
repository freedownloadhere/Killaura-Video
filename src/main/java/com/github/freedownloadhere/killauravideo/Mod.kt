package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.commands.CommandAddFake
import com.github.freedownloadhere.killauravideo.commands.CommandToggle
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = "killauravideo", useMetadata = true)
class Mod {
    @Suppress("UNUSED_PARAMETER")
    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(GlobalManager)
        ClientCommandHandler.instance.registerCommand(CommandToggle())
        ClientCommandHandler.instance.registerCommand(CommandAddFake())
    }
}