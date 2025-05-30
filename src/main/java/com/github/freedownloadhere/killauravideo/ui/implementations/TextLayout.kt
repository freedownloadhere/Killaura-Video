package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.misc.IText
import net.minecraft.client.Minecraft

class TextLayout<T>
    : ILayoutPost
        where T: UI, T: IText
{
    @Suppress("UNCHECKED_CAST")
    override fun applyLayoutPost() {
        with(this as T) {
            val fr = Minecraft.getMinecraft().fontRendererObj
            width = scale.numeric * fr.getStringWidth(text).toDouble()
            height = scale.numeric * fr.FONT_HEIGHT.toDouble()
        }
    }
}