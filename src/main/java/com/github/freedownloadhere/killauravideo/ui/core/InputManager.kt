package com.github.freedownloadhere.killauravideo.ui.core

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
        lastMouseY = GlobalManager.core!!.height - Mouse.getEventY() - 1
        lastDwheel = Mouse.getEventDWheel()
    }
}