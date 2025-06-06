package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.modules.Killaura
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.dsl.*
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.util.Config
import com.github.freedownloadhere.killauravideo.ui.util.TimeUtil
import com.github.freedownloadhere.killauravideo.ui.util.round
import com.github.freedownloadhere.killauravideo.utils.Chat
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import java.time.Instant

class Core: GuiScreen() {
    init {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
    }

    val config = Config(screenWidth = width.toDouble(), screenHeight = height.toDouble())
    private lateinit var topLevelUI: UI

    private val mouseInfo = MouseInfo()

    private lateinit var interactionManager: InteractionManager
    lateinit var renderer: Renderer

    private val timeUtil = TimeUtil()

    override fun initGui() {
        topLevelUI = verticalBox {
            val ka = GlobalManager.clientInstance!!.moduleMap.module("killaura") as Killaura

            + centerBox {
                child = text("Killaura")
            }

            + verticalBox {
                spacing = 10.0
                + button {
                    text.source = { "Toggle" }
                    clickAction = { ka.toggle() }
                }

                + text { source = { "Reach: ${ka.limiter.maxReach}" } }
                + slider {
                    minValue = 3.0
                    maxValue = 6.0
                    segmented = true
                    segmentCount = 6
                    selectedValue = ka.limiter.maxReach
                    clickAction = { ka.limiter.maxReach = selectedValue }
                }

                + text { source = { "FOV: ${ka.limiter.maxFov}" } }
                + slider {
                    minValue = 30.0
                    maxValue = 180.0
                    segmented = true
                    segmentCount = 15
                    selectedValue = ka.limiter.maxFov.toDouble()
                    clickAction = { ka.limiter.maxFov = selectedValue.toFloat() }
                }
            }
        }

        interactionManager = InteractionManager(mouseInfo, topLevelUI)
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
        mouseInfo.update()
        interactionManager.handleMouseInput()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        interactionManager.handleKeyTyped(typedChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean = false
}