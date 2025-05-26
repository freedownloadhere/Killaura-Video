package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.containers.UIFreeBox
import com.github.freedownloadhere.killauravideo.ui.core.io.InputManager
import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.rendering.Renderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayout
import com.github.freedownloadhere.killauravideo.ui.util.Config
import com.github.freedownloadhere.killauravideo.ui.util.TimeUtil
import com.github.freedownloadhere.killauravideo.ui.util.ui
import com.github.freedownloadhere.killauravideo.utils.Chat
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager

class Core : GuiScreen() {
    init {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
    }

    val config = Config(screenWidth = width.toDouble(), screenHeight = height.toDouble())
    private lateinit var ui: UI

    private val inputManager = InputManager()

    private lateinit var interactionManager: InteractionManager
    lateinit var renderer: Renderer

    private val timeUtil = TimeUtil()

    override fun initGui() {
        ui = ui {
            vbox {
                hbox {
                    button {
                        text = "button 1"
                        action = { Chat.addMessage("button 1", "actioned") }
                    }
                    text("hehehe")
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

        interactionManager = InteractionManager(inputManager, ui)
        renderer = Renderer(config, interactionManager)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val deltaTime = timeUtil.newDeltaTime()

        drawDefaultBackground()

        renderer.withUIState {
            if(ui is ILayout)
                (ui as ILayout).applyLayout()
            ui.updateRecursive(deltaTime)
            ui.renderRecursive()
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