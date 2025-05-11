package com.github.freedownloadhere.killauravideo.utils

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object Chat {
    fun addMessage(category: String, msg: String) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText("\u00A7l[$category]\u00A7r \u00A77$msg")
        )
    }

    fun error(msg: String) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText("\u00A7c\u00A7l[Error]\u00A7r \u00A77$msg")
        )
    }
}