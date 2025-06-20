package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.core.io.UIInteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UIRenderManager
import com.github.freedownloadhere.killauravideo.ui.dsl.UIBuilderGlobals
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import java.time.Instant

class UICore(
    val config: UIStyleConfig = UIStyleConfig(),
    private val uiWidgetProvider: () -> UIWidget
): GuiScreen()
{
    // future reminder:
    // the reason why this is all mutable is because
    // when the screen resizes it needs to keep track of
    // the new width and height

    private lateinit var mainUIWidget: UIWidget

    private lateinit var interactionManager: UIInteractionManager
    private lateinit var renderManager: UIRenderManager

    override fun initGui() {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight

        config.screenWidth = width.toFloat()
        config.screenHeight = height.toFloat()

        RenderingBackend.updateScreenSize(
            config.screenWidth,
            config.screenHeight
        )

        UIBuilderGlobals.uiConfig = config

        mainUIWidget = uiWidgetProvider()

        interactionManager = UIInteractionManager(mainUIWidget, config)
        renderManager = UIRenderManager(config)
    }

    private var lastTime: Long = Instant.now().toEpochMilli()
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val newTime = Instant.now().toEpochMilli()
        val deltaTime = newTime - lastTime
        lastTime = newTime

        drawDefaultBackground()

        interactionManager.handleMouseInput()

        if(mainUIWidget is ILayout)
            (mainUIWidget as ILayout).applyLayout()

        mainUIWidget.updateRecursive(deltaTime)

        renderManager.renderEverything(mainUIWidget)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        interactionManager.handleKeyTyped(typedChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean = false
}