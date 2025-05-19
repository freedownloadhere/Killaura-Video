package com.github.freedownloadhere.killauravideo.ui.utils

import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.ui.UIListContainer
import com.github.freedownloadhere.killauravideo.ui.UICore
import net.minecraft.client.Minecraft

object UITextUtils {
    fun wordWrap(str : String, parent : UI, scaleMult : Double = 1.0) : List<String> {
        val fr = Minecraft.getMinecraft().fontRendererObj
        val ts = scaleMult * UICore.config.textScale
        val strList = mutableListOf<String>()
        val rect = UILayoutUtils.Rectangle(parent)
        if(parent is UIListContainer)
            rect.scale(1.0 - 2.0 * UICore.config.listSpacingScale)

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