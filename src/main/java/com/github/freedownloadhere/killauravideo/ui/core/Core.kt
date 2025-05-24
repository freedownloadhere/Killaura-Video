package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.demo.DemoBuilder
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

class Core : GuiScreen() {
    init {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
    }

    private val ui = DemoBuilder().base

    val config = Config(screenWidth = width.toDouble(), screenHeight = height.toDouble())
    private val inputManager = InputManager()
    private val interactionManager = InteractionManager(inputManager, ui)
    val renderer = Renderer(config, interactionManager)

    private val timeUtil = TimeUtil()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val deltaTime = timeUtil.newDeltaTime()

        drawDefaultBackground()

        renderer.withUIState {
            ui.applyLayout()
            ui.update(deltaTime)
        }
    }

    override fun handleMouseInput() {
        inputManager.updateMouse()
        interactionManager.handleMouseInput()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        interactionManager.handleKeyTyped(typedChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean = false
}