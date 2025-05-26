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
        super.initGui()
        ui = ui {
            vbox {
                hbox {
                    onLeft {
                        text("gurt:") { scale = UIText.Scale.LARGE }
                        text("yo")
                    }
                    onRight {
                        centerbox(text("epic")) { padded = true }
                        button {
                            text = "this is a button"
                            action = { Chat.addMessage("Button", "Yoo") }
                        }
                    }
                }
                freebox {
                    width = 200.0
                    height = 150.0
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