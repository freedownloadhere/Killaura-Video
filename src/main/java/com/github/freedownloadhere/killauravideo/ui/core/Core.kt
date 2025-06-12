package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.modules.Killaura
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIIcon
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.core.io.InteractionManager
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
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
    lateinit var renderer: UINewRenderer

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
        renderer = UINewRenderer(config)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val deltaTime = timeUtil.newDeltaTime()

        drawDefaultBackground()

        JavaNativeRendering.nUpdateScreenSize(width.toFloat(), height.toFloat())

        JavaNativeRendering.nDrawRect(
            100.0f, 100.0f, 0.0f,
            200.0f, 100.0f,
            UIColorEnum.BOX_SECONDARY.toColor(), UIColorEnum.BOX_PRIMARY.toColor(),
            10.0f, 1.0f
        )

//        if(topLevelUI is ILayout)
//            (topLevelUI as ILayout).applyLayout()
//
//        topLevelUI.updateRecursive(deltaTime)
//
//        renderer.renderEverything(topLevelUI)
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