package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.ui.UIListContainer
import net.minecraft.client.Minecraft

object TextUtils {
    fun wordWrap(str : String, parent : UI, scaleMult : Double = 1.0) : List<String> {
        val fr = Minecraft.getMinecraft().fontRendererObj
        val ts = scaleMult * Core.config.textScale
        val strList = mutableListOf<String>()
        val rect = LayoutUtils.Rectangle(parent)
        if(parent is UIListContainer)
            rect.scale(1.0 - 2.0 * Core.config.listSpacingScale)

        var width = 0.0
        val buffer = StringBuilder()
        for(c in str) {
            val chw = fr.getCharWidth(c) * ts
            if(width + chw > rect.w) {
                width = 0.0
                val newRow = buffer.toString()
                strList.add(newRow)
                buffer.clear()
            }
            buffer.append(c)
            width += fr.getCharWidth(c) * ts
        }

        if(buffer.isNotBlank()) {
            val newRow = buffer.toString()
            strList.add(newRow)
        }

        return strList
    }
}