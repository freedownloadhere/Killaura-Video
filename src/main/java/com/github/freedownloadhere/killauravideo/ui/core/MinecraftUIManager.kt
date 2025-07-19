package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.core.io.IKeyboard
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouse
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UIRenderManager
import com.github.freedownloadhere.killauravideo.ui.dsl.UIBuilderGlobals
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import java.time.Instant

class MinecraftUIManager(
    val config: UIStyleConfig = UIStyleConfig(),
    private val uiWidgetProvider: () -> UIWidget
): GuiScreen()
{
    private val lwjglMouse = object : IMouse {
        override val x: Int
            get() = Mouse.getEventX()
        override val y: Int
            get() = Mouse.getEventY()
        override val scroll: Int
            get() = Mouse.getEventDWheel()
        override val isLcmDown: Boolean
            get() = Mouse.isButtonDown(0)
        override val isRcmDown: Boolean
            get() = Mouse.isButtonDown(1)
    }

    private val lwjglKeyboard = object : IKeyboard {
        override val eventState: IKeyboard.EventState
            get() {
                val pressed = Keyboard.getEventKeyState()
                val repeat = Keyboard.isRepeatEvent()
                return if(!pressed)
                    IKeyboard.EventState.RELEASE
                else if(!repeat)
                    IKeyboard.EventState.PRESS
                else
                    IKeyboard.EventState.REPEAT
            }
        override val eventChar: Char
            get() = Keyboard.getEventCharacter()
        override val eventKey: Int
            get() = Keyboard.getEventKey()
    }

    private lateinit var uiManager: UIManager

    override fun initGui() {
        width = Minecraft.getMinecraft().displayWidth
        height = Minecraft.getMinecraft().displayHeight

        config.screenWidth = width.toFloat()
        config.screenHeight = height.toFloat()

        RenderingBackend.updateScreenSize(
            config.screenWidth,
            config.screenHeight
        )

        uiManager = UIManager(
            lwjglMouse,
            lwjglKeyboard,
            config,
            uiWidgetProvider
        )

        uiManager.requestUI()
    }

    private var lastTime: Long = Instant.now().toEpochMilli()
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val newTime = Instant.now().toEpochMilli()
        val deltaTime = newTime - lastTime
        lastTime = newTime

        drawDefaultBackground()

        with(uiManager) {
            updateInput()
            updateUILayout()
            updateUI(deltaTime)
            renderUI()
        }
    }

    override fun doesGuiPauseGame(): Boolean = false
}