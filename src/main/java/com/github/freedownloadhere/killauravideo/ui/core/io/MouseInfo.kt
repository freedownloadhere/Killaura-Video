package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IInteractable
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import org.lwjgl.input.Mouse

class MouseInfo {
    data class UIAbsoluteData(val ui: UI, val absX: Double, val absY: Double)

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

    val isClicked: Boolean
        get() = Mouse.getEventButtonState()
    val buttonmask: Int
        get() = Mouse.getEventButton()
    val isHeldDown: Boolean
        get() = Mouse.isButtonDown(0)

    var currentHovered: UIAbsoluteData? = null
        private set
    var lastLeftClicked: UIAbsoluteData? = null
        private set

    fun update(topUI: UI, config: UIConfig) {
        val newX = Mouse.getEventX()
        val newY = config.screenHeight.toInt() - Mouse.getEventY() - 1
        dX = newX - lastX
        dY = newY - lastY
        lastX = newX
        lastY = newY
        lastDwheel = Mouse.getEventDWheel()
        val maybeMouseCurrentlyOn = topUI.findMouseCurrentlyOn(0.0, 0.0)
        if(maybeMouseCurrentlyOn != null) {
            currentHovered = maybeMouseCurrentlyOn
            
        }
    }

    private fun UI.findMouseCurrentlyOn(absX: Double, absY: Double): UIAbsoluteData? {
        if(!active) return null

        if(this is IParent)
            for(child in children) {
                val maybeMouseOn = child.findMouseCurrentlyOn(absX + relX, absY + relY)
                if(maybeMouseOn != null)
                    return maybeMouseOn
            }

        if(absX + relX <= lastX && lastX <= absX + relX + width)
            if(absY + relY <= lastY && lastY <= absY + relY + height)
                if(this is IInteractable)
                    return UIAbsoluteData(this, absX + relX, absY + relY)

        return null
    }
}