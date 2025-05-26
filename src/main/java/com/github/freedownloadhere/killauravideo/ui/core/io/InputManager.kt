package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.GlobalManager
import org.lwjgl.input.Mouse

class InputManager {
    var lastMouseX = -1
        private set
    var lastMouseY = -1
        private set
    var lastDwheel = 0
        private set
    val scrollSens = 10

    val mouseIsClicked : Boolean
        get() = Mouse.getEventButtonState()
    val mouseButtonMask : Int
        get() = Mouse.getEventButton()

    fun updateMouse() {
        lastMouseX = Mouse.getEventX()
        lastMouseY = GlobalManager.core!!.config.screenHeight.toInt() - Mouse.getEventY() - 1
        lastDwheel = Mouse.getEventDWheel()
    }
}