package com.github.freedownloadhere.killauravideo.ui.core.rendering

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.mixin.AccessorFontRenderer
import com.github.freedownloadhere.killauravideo.ui.util.Config
import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
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

    fun <T> drawBasicBG(gui: T) where T: UI, T: IDrawable {
        if(gui == interactionManager.focused)
            drawHL(gui)
        else
            drawBorder(gui)
        drawBG(gui, gui.baseColor)
    }

    private fun drawHL(ui: UI) = drawUIrect(ui, ColorHelper.GuiPrimary, filled = false)
    private fun drawBorder(ui: UI) = drawUIrect(ui, ColorHelper.GuiNeutralLight, filled = false)
    private fun drawBG(ui: UI, col: ColorHelper) = drawUIrect(ui, col, filled = true)

    private fun drawUIrect(ui: UI, col: ColorHelper, filled: Boolean) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW)
        GlStateManager.pushMatrix()
        GlStateManager.scale(ui.width, ui.height, 1.0)
        drawRect(col, filled)
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

//        scissorStack.enable()

        block()

//        scissorStack.disable()

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

        Minecraft.getMinecraft().textureManager.bindTexture(fontTex)

        block()

        GlStateManager.disableBlend()
        GlStateManager.disableTexture2D()
    }

    private fun drawRect(col: ColorHelper, filled: Boolean) {
        val worldRenderer = Tessellator.getInstance().worldRenderer
        val type = if(filled) GL11.GL_QUADS else GL11.GL_LINE_LOOP
        worldRenderer.begin(type, DefaultVertexFormats.POSITION_COLOR)
        worldRenderer.pos(0.0, 0.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        worldRenderer.pos(0.0, 1.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        worldRenderer.pos(1.0, 1.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        worldRenderer.pos(1.0, 0.0, 0.0).color(col.r, col.g, col.b, col.a).endVertex()
        Tessellator.getInstance().draw()
    }
}