package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.io.InputManager
import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.util.Config
import com.github.freedownloadhere.killauravideo.ui.util.TimeUtil
import com.github.freedownloadhere.killauravideo.ui.util.ui
import com.github.freedownloadhere.killauravideo.utils.Chat
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

class Core : GuiScreen() {
    init {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
    }

    val config = Config(screenWidth = width.toDouble(), screenHeight = height.toDouble())
    private lateinit var topLevelUI: UI

    private val inputManager = InputManager()

    private lateinit var interactionManager: InteractionManager
    lateinit var renderer: Renderer

    private val timeUtil = TimeUtil()

    override fun initGui() {
        topLevelUI = ui {
            vbox {
                relX = 200.0
                relY = 100.0
                hbox {
                    onLeft { button {
                        text = "button 1"
                        action = { Chat.addMessage("button 1", "actioned") }
                    } }
                    onRight { text("hehehe") }
                }
                hbox {
                    button {
                        text = "button 2 yoo"
                        action = { Chat.addMessage("button 2", "actioned") }
                    }
                    text("redundants")
                }
            }
        }

        interactionManager = InteractionManager(inputManager, topLevelUI)
        renderer = Renderer(config, interactionManager)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val deltaTime = timeUtil.newDeltaTime()

        drawDefaultBackground()

        renderer.withUIState {
            if(topLevelUI is ILayout)
                (topLevelUI as ILayout).applyLayout()
            topLevelUI.updateRecursive(deltaTime)
            topLevelUI.renderRecursive()
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