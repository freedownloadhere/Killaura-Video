package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.mixin.AccessorFontRenderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

class Renderer(
    private val config: Config,
    private val interactionManager: InteractionManager
)
{
    companion object {
        val renderIterator = RecursiveIterator(
            onBegin = { GlStateManager.translate(relX, relY, 0.0) },
            onEnd = { GlStateManager.translate(-relX, -relY, 0.0) }
        )
    }

    val scissorStack = RenderScissorStack()

    fun <T> drawBasicBG(gui : T) where T: UI, T: IDrawable {
        if(gui == interactionManager.focused)
            drawHL(gui)
        else
            drawBorder(gui)
        drawBG(gui, gui.baseColor)
    }

    private fun drawBorder(gui : UI, col : ColorHelper = ColorHelper.GuiNeutralLight) {
        val t = config.borderThickness
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.translate(gui.relX - t, gui.relY - t, 0.0)
        GlStateManager.scale(gui.width + 2 * t, gui.height + 2 * t, 1.0)
        drawRect(col)
        GlStateManager.popMatrix()
    }

    private fun drawBG(gui : UI, col : ColorHelper = ColorHelper.GuiNeutral) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.translate(gui.relX, gui.relY, 0.0)
        GlStateManager.scale(gui.width, gui.height, 1.0)
        drawRect(col)
        GlStateManager.popMatrix()
    }

    private fun drawHL(gui : UI) {
        val t1 = GlobalManager.core!!.config.borderThickness
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.translate(gui.relX - t1, gui.relY - t1, 0.0)
        GlStateManager.scale(gui.width + 2 * t1, gui.height + 2 * t1, 1.0)
        drawRect(ColorHelper.GuiPrimary)
        GlStateManager.popMatrix()
    }

    fun withUIState(block: () -> Unit) {
        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()
        GlStateManager.ortho(0.0, config.screenWidth, config.screenHeight, 0.0, -1.0, 1.0)

        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()

        GlStateManager.disableTexture2D()
        GlStateManager.disableLighting()

        scissorStack.enable()

        block()

        scissorStack.disable()

        GlStateManager.enableLighting()
        GlStateManager.enableTexture2D()

        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.popMatrix()
        GlStateManager.matrixMode(GL11.GL_PROJECTION)
        GlStateManager.popMatrix()
    }

    fun withTextState(block: () -> Unit) {
        val fr = Minecraft.getMinecraft().fontRendererObj
        val fontTex = (fr as AccessorFontRenderer).fontLocation_killauravideo

        GlStateManager.enableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()

        Minecraft.getMinecraft().textureManager.bindTexture(fontTex)

        block()

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