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
    lateinit var config: UIConfig
        private set
    private lateinit var topLevelUI: UI

    private val mouseInfo = MouseInfo()

    private lateinit var interactionManager: InteractionManager
    lateinit var renderer: UINewRenderer

    private val timeUtil = TimeUtil()

    override fun initGui() {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight
        config = UIConfig(screenWidth = width.toDouble(), screenHeight = height.toDouble())
        UIBuilderGlobals.uiConfig = config

//        topLevelUI = verticalBox {
//            relX = 300.0
//            relY = 300.0
//            + text("Very Large") { scale = UIText.Scale.VERY_LARGE }
//            + text("Large") { scale = UIText.Scale.LARGE }
//            + text("Medium") { scale = UIText.Scale.MEDIUM }
//            + text("Small") { scale = UIText.Scale.SMALL }
//            + text("Very Small") { scale = UIText.Scale.VERY_SMALL }
//        }

        topLevelUI = verticalBox {
            val ka = GlobalManager.clientInstance!!.moduleMap.module("killaura") as Killaura

            + centerBox {
                child = text("Killaura") {
                    scale = UIText.Scale.SMALL
                }
                width = 200.0
            }

            + verticalBox {
                padding = 0.0
                hidden = true

                + horizontalBox {
                    placeLeft()
                    + text("Toggled") {
                        scale = UIText.Scale.SMALL
                    }
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
                        + text("Reach") {
                            scale = UIText.Scale.SMALL
                        }
                        placeRight()
                        + text {
                            scale = UIText.Scale.SMALL
                            source = { ka.limiter.maxReach.toString() }
                        }
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


//        topLevelUI = verticalBox {
//            canBeMoved = true
//            + verticalBox {
//                baseColor = UIColorEnum.BOX_SECONDARY
//                + text { source = {"Mouse Info Info"}; scale = UIText.Scale.LARGE }
//            }
//            + verticalBox {
//                + text { source = {"DX: ${mouseInfo.dX}"} }
//                + text { source = {"DY: ${mouseInfo.dY}"} }
//                + text { source = {"LastX: ${mouseInfo.lastX}"} }
//                + text { source = {"LastY: ${mouseInfo.lastY}"} }
//                + text { source = {"Left Click"} }
//                + text { source = {"Hold time: ${mouseInfo.lcmHoldTime}"} }
//                + text { source = {"Instant: ${mouseInfo.lcmInstant?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Current: ${mouseInfo.lcmCurrent?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Grabbed: ${mouseInfo.lcmGrabbed?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Hovered: ${mouseInfo.lcmHovered?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Right Click"} }
//                + text { source = {"Hold time: ${mouseInfo.rcmHoldTime}"} }
//                + text { source = {"Instant: ${mouseInfo.rcmInstant?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Current: ${mouseInfo.rcmCurrent?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Grabbed: ${mouseInfo.rcmGrabbed?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//                + text { source = {"Hovered: ${mouseInfo.rcmHovered?.ui?.hashCode()}"}; scale = UIText.Scale.SMALL }
//            }
//        }

        interactionManager = InteractionManager(mouseInfo, topLevelUI, config)
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