package com.github.freedownloadhere.killauravideo.ui.utils

import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.mixin.AccessorFontRenderer
import com.github.freedownloadhere.killauravideo.ui.UICore
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.UICore.height
import com.github.freedownloadhere.killauravideo.ui.UICore.width
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

class UIRenderer {
    val scissorStack = RenderScissorStack()

    fun drawBasicBG(gui : UI) {
        if(gui !is IDrawable)
            return
        if(gui == UICore.interactionManager.focused)
            drawHL(gui)
        else
            drawBorder(gui)
        drawBG(gui, gui.baseColor)
    }

    fun drawBorder(gui : UI, col : ColorHelper = ColorHelper.GuiNeutralLight) {
        val t = UICore.config.borderThickness
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.translate(gui.x - t, gui.y - t, 0.0)
        GlStateManager.scale(gui.w + 2 * t, gui.h + 2 * t, 1.0)
        drawRect(col)
        GlStateManager.popMatrix()
    }

    fun drawBG(gui : UI, col : ColorHelper = ColorHelper.GuiNeutral) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.translate(gui.x, gui.y, 0.0)
        GlStateManager.scale(gui.w, gui.h, 1.0)
        drawRect(col)
        GlStateManager.popMatrix()
    }

    fun drawHL(gui : UI) {
        val t1 = UICore.config.borderThickness
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.translate(gui.x - t1, gui.y - t1, 0.0)
        GlStateManager.scale(gui.w + 2 * t1, gui.h + 2 * t1, 1.0)
        drawRect(ColorHelper.GuiPrimary)
        GlStateManager.popMatrix()
    }

    fun beginGuiState() {
        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()
        GlStateManager.ortho(0.0, width.toDouble(), height.toDouble(), 0.0, -1.0, 1.0)

        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()

        GlStateManager.disableTexture2D()
        GlStateManager.disableLighting()

        scissorStack.enable()
    }

    fun endGuiState() {
        scissorStack.disable()

        GlStateManager.enableLighting()
        GlStateManager.enableTexture2D()

        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.popMatrix()
        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.popMatrix()
    }

    fun beginTextState() {
        val fr = Minecraft.getMinecraft().fontRendererObj
        val fontTex = (fr as AccessorFontRenderer).fontLocation_killauravideo

        GlStateManager.enableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()

        Minecraft.getMinecraft().textureManager.bindTexture(fontTex)
    }

    fun endTextState() {
        GlStateManager.disableBlend()
        GlStateManager.disableTexture2D()
        GlStateManager.popMatrix()
    }

    private fun drawRect(col : ColorHelper) {
        val worldRenderer = Tessellator.getInstance().worldRenderer
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
        worldRenderer.pos(0.0, 0.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        worldRenderer.pos(0.0, 1.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        worldRenderer.pos(1.0, 1.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        worldRenderer.pos(1.0, 0.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        Tessellator.getInstance().draw()
    }
}