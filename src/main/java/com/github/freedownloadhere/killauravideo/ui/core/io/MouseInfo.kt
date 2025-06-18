package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IInteractable
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import org.lwjgl.input.Mouse

class MouseInfo {
    data class UIAbsoluteData(val ui: UI, val absX: Float, val absY: Float)

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

    var lcmHoldTime = 0
        private set
    var lcmGrabbed: UIAbsoluteData? = null
        private set
    var lcmCurrent: UIAbsoluteData? = null
        private set
    var lcmInstant: UIAbsoluteData? = null
        private set
    var lcmHovered: UIAbsoluteData? = null
        private set

    var rcmHoldTime = 0
        private set
    var rcmGrabbed: UIAbsoluteData? = null
        private set
    var rcmCurrent: UIAbsoluteData? = null
        private set
    var rcmInstant: UIAbsoluteData? = null
        private set
    var rcmHovered: UIAbsoluteData? = null
        private set

    fun update(topUI: UI, config: UIStyleConfig) {
        val newX = Mouse.getEventX()
        val newY = config.screenHeight.toInt() - Mouse.getEventY() - 1
        dX = newX - lastX
        dY = newY - lastY
        lastX = newX
        lastY = newY
        lastDwheel = Mouse.getEventDWheel()

        lcmHoldTime = if(Mouse.isButtonDown(0)) lcmHoldTime + 1 else 0
        rcmHoldTime = if(Mouse.isButtonDown(1)) rcmHoldTime + 1 else 0

        lcmInstant = null
        rcmInstant = null

        lcmCurrent = null
        rcmCurrent = null

        if(lcmHoldTime == 0) lcmGrabbed = null
        if(rcmHoldTime == 0) rcmGrabbed = null

        lcmHovered = null
        rcmHovered = null

        val freshUIData = topUI.findMouseCurrentlyOn() ?: return

        lcmInstant = if(lcmHoldTime == 1) freshUIData else null
        rcmInstant = if(rcmHoldTime == 1) freshUIData else null

        lcmCurrent = if(lcmHoldTime > 0) freshUIData else null
        rcmCurrent = if(rcmHoldTime > 0) freshUIData else null

        if(lcmHoldTime > 0 && lcmGrabbed == null) lcmGrabbed = freshUIData
        if(rcmHoldTime > 0 && rcmGrabbed == null) rcmGrabbed = freshUIData

        lcmHovered = freshUIData
        rcmHovered = freshUIData
    }

    private fun UI.findMouseCurrentlyOn(absX: Float = 0.0f, absY: Float = 0.0f): UIAbsoluteData? {
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