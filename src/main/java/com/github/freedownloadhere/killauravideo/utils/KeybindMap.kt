package com.github.freedownloadhere.killauravideo.utils

import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.fml.client.registry.ClientRegistry

object KeybindMap {
    private val keybindMap = mutableMapOf<KeyBinding, () -> Unit>()

    fun addKey(key: KeyBinding, callback: () -> Unit) {
        keybindMap[key] = callback
        ClientRegistry.registerKeyBinding(key)
    }

    fun update() {
        for((key, callback) in keybindMap)
            if(key.isPressed)
                callback()
    }
}