package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.GlobalManager
import org.lwjgl.input.Mouse

class MouseInfo {
    var lastX = -1
        private set
    var lastY = -1
        private set
    var lastDwheel = 0
        private set
    var dX = 0
        private set
    var dY = 0
        private set
    val scrollSens = 10

    val isClicked : Boolean
        get() = Mouse.getEventButtonState()
    val buttonmask : Int
        get() = Mouse.getEventButton()
    val isHeldDown: Boolean
        get() = Mouse.isButtonDown(0)

    fun update() {
        val newX = Mouse.getEventX()
        val newY = GlobalManager.core!!.config.screenHeight.toInt() - Mouse.getEventY() - 1
        dX = newX - lastX
        dY = newY - lastY
        lastX = newX
        lastY = newY
        lastDwheel = Mouse.getEventDWheel()
    }
}