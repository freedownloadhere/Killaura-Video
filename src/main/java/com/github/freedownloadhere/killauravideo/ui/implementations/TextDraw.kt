package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.misc.IText
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager

class TextDraw<T>
    : IDrawable
        where T: UI, T: IText
{
    override var hidden = false
    override var baseColor = UIColorEnum.WHITE

    @Suppress("UNCHECKED_CAST")
    override fun draw() {
        with(this as T) {
            if (text.isEmpty()) return
            GlobalManager.core!!.renderer.withTextState {
                val fr = Minecraft.getMinecraft().fontRendererObj
                GlStateManager.pushMatrix()
                GlStateManager.scale(scale.numeric, scale.numeric, 1.0)
                fr.drawStringWithShadow(text, 0.0f, 0.0f, baseColor.toPackedARGB())
                GlStateManager.popMatrix()
            }
        }
    }
}