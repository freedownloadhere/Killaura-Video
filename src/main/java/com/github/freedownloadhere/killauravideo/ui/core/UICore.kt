package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.dsl.UIBuilderGlobals
import com.github.freedownloadhere.killauravideo.ui.util.TimeUtil
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

class UICore(private val uiProvider: () -> UI): GuiScreen() {
    // future reminder:
    // the reason why this is all mutable is because
    // when the screen resizes it needs to keep track of
    // the new width and height

    private lateinit var config: UIStyleConfig

    private lateinit var topLevelUI: UI

    private lateinit var interactionManager: InteractionManager
    private lateinit var renderer: UINewRenderer

    private val timeUtil = TimeUtil()

    override fun initGui() {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight

        config = UIStyleConfig(
            screenWidth = width.toFloat(),
            screenHeight = height.toFloat()
        )

        UIBuilderGlobals.uiConfig = config

        topLevelUI = uiProvider()

        interactionManager = InteractionManager(topLevelUI, config)
        renderer = UINewRenderer(config)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val deltaTime = timeUtil.newDeltaTime()

        drawDefaultBackground()

        JavaNativeRendering.nUpdateScreenSize(width.toFloat(), height.toFloat())

        interactionManager.handleMouseInput()

        if(topLevelUI is ILayout)
            (topLevelUI as ILayout).applyLayout()

        topLevelUI.updateRecursive(deltaTime)

        renderer.renderEverything(topLevelUI)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        interactionManager.handleKeyTyped(typedChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean = false
}