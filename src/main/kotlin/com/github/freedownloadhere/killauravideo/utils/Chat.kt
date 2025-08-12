package com.github.freedownloadhere.killauravideo.utils

import com.github.freedownloadhere.killauravideo.interfaces.INamed
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

object Chat
{
    fun info(category: INamed, msg: String) {
        val name = category.name
        info(name, msg)
    }

    fun info(category: String, msg: String) {
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