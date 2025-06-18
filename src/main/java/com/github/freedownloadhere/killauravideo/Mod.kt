package com.github.freedownloadhere.killauravideo

import com.github.freedownloadhere.killauravideo.commands.CommandToggle
import com.github.freedownloadhere.killauravideo.ui.core.UICore
import com.github.freedownloadhere.killauravideo.ui.dsl.text
import com.github.freedownloadhere.killauravideo.utils.KeybindMap
import com.github.freedownloadhere.killauravideo.utils.LibraryLoading
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.lwjgl.input.Keyboard

@Mod(modid = "killauravideo", useMetadata = true)
class Mod {
    @Suppress("UNUSED_PARAMETER")
    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        LibraryLoading.load()

        MinecraftForge.EVENT_BUS.register(GlobalManager)
        ClientCommandHandler.instance.registerCommand(CommandToggle())

        KeybindMap.addKey(KeyBinding("Toggle UI", Keyboard.KEY_J, "")) {
            GlobalManager.core = UICore {
                text("hello")
            }
            Minecraft.getMinecraft().displayGuiScreen(GlobalManager.core!!)
        }
    }
}