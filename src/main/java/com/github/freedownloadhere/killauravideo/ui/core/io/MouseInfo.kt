package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.GlobalManager
import org.lwjgl.input.Mouse

class MouseInfo {
    var lastMouseX = -1
        private set
    var lastMouseY = -1
        private set
    var lastDwheel = 0
        private set
    var mouseDX = 0
        private set
    var mouseDY = 0
        private set
    val scrollSens = 10

    val mouseIsClicked : Boolean
        get() = Mouse.getEventButtonState()
    val mouseButtonMask : Int
        get() = Mouse.getEventButton()
    val mouseIsDown: Boolean
        get() = Mouse.isButtonDown(0)

    fun updateMouse() {
        val newMouseX = Mouse.getEventX()
        val newMouseY = GlobalManager.core!!.config.screenHeight.toInt() - Mouse.getEventY() - 1
        mouseDX = newMouseX - lastMouseX
        mouseDY = newMouseY - lastMouseY
        lastMouseX = newMouseX
        lastMouseY = newMouseY
        lastDwheel = Mouse.getEventDWheel()
    }
}