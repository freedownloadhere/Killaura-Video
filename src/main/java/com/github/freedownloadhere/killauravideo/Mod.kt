package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.commands.CommandToggle
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = "killauravideo", useMetadata = true)
class Mod {
    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(Manager)
        ClientCommandHandler.instance.registerCommand(CommandToggle())
    }
}