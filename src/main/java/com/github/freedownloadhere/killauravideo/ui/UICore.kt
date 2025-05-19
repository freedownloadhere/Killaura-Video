package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.utils.*
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

object UICore : GuiScreen() {
    private var base : UIWindow? = null
    private lateinit var timeUtil : UITimeUtil
    private lateinit var inputManager : UIInputManager
    lateinit var interactionManager : UIInteractionManager
        private set
    lateinit var renderer : UIRenderer
        private set
    lateinit var config : UIConfig
        private set

    override fun initGui() {
        super.initGui()
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
        config = UIConfig()
        base = UIDemoWindow().base
        timeUtil = UITimeUtil()
        inputManager = UIInputManager()
        interactionManager = UIInteractionManager(inputManager, base)
        renderer = UIRenderer()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val deltaTime = timeUtil.newDeltaTime()

        drawDefaultBackground()
        renderer.beginGuiState()
        base?.applyLayout()
        base?.update(deltaTime)
        renderer.endGuiState()
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