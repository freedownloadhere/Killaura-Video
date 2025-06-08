package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.modules.Killaura
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIIcon
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.SomeTestRenderer
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.dsl.*
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.util.TimeUtil
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

class Core: GuiScreen() {
    init {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
    }

    val config = UIConfig(screenWidth = width.toDouble(), screenHeight = height.toDouble())
    private lateinit var topLevelUI: UI

    private val mouseInfo = MouseInfo()

    private lateinit var interactionManager: InteractionManager
    lateinit var renderer: Renderer

    private val timeUtil = TimeUtil()

    override fun initGui() {
        UIBuilderGlobals.uiConfig = config

        topLevelUI = verticalBox {
            val ka = GlobalManager.clientInstance!!.moduleMap.module("killaura") as Killaura

            + centerBox {
                child = text("Killaura")
                width = 200.0
            }

            + verticalBox {
                padding = 0.0
                hidden = true

                + horizontalBox {
                    placeLeft()
                    + text("Toggled")
                    placeRight()
                    + checkbox {
                        checked = ka.toggled
                        child.scale = UIIcon.Scale.SMALL
                        onCheck = { ka.toggle() }
                    }
                }

                + verticalBox {
                    spacing = 5.0

                    val minReach = 3.0
                    val maxReach = 6.0

                    + horizontalBox {
                        padding = 0.0
                        hidden = true
                        placeLeft()
                        + text("Reach")
                        placeRight()
                        + text { source = { ka.limiter.maxReach.toString() } }
                    }
                    + horizontalBox {
                        padding = 0.0
                        hidden = true
                        placeLeft()
                        + text {
                            source = { minReach.toString() }
                            scale = UIText.Scale.SMALL
                            baseColor = UIColorEnum.TEXT_SECONDARY
                        }
                        placeRight()
                        + text {
                            source = { maxReach.toString() }
                            scale = UIText.Scale.SMALL
                            baseColor = UIColorEnum.TEXT_SECONDARY
                        }
                    }
                    + slider {
                        minValue = minReach
                        maxValue = maxReach
                        segmented = true
                        segmentCount = 6
                        selectedValue = ka.limiter.maxReach
                        clickAction = { ka.limiter.maxReach = selectedValue }
                    }
                }
            }
        }

        interactionManager = InteractionManager(mouseInfo, topLevelUI)
        renderer = Renderer(config, interactionManager)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
//        val deltaTime = timeUtil.newDeltaTime()
//
//        drawDefaultBackground()
//
//        renderer.withUIState {
//            if(topLevelUI is ILayout)
//                (topLevelUI as ILayout).applyLayout()
//            topLevelUI.updateRecursive(deltaTime)
//            topLevelUI.renderRecursive(renderer)
//        }

        // TODO remove
        SomeTestRenderer.renderTriangle()
    }

    override fun handleMouseInput() {
        mouseInfo.update(topLevelUI, config)
        interactionManager.handleMouseInput()
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        super.keyTyped(typedChar, keyCode)
        interactionManager.handleKeyTyped(typedChar, keyCode)
    }

    override fun doesGuiPauseGame(): Boolean = false
}