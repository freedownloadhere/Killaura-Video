package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import org.lwjgl.input.Keyboard

class InputData(
    private val mouse: IMouse,
    private val keyboard: IKeyboard,
) {
    data class UIAbsoluteData(val uiWidget: UIWidget, val absX: Float, val absY: Float)

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

    var hovered: UIAbsoluteData? = null
        private set

    var lcmHoldTime = 0
        private set
    var lcmSelected: UIAbsoluteData? = null
        private set
    var lcmGrabbed: UIAbsoluteData? = null
        private set
    var lcmCurrent: UIAbsoluteData? = null
        private set
    var lcmInstant: UIAbsoluteData? = null
        private set

    var rcmHoldTime = 0
        private set
    var rcmSelected: UIAbsoluteData? = null
        private set
    var rcmGrabbed: UIAbsoluteData? = null
        private set
    var rcmCurrent: UIAbsoluteData? = null
        private set
    var rcmInstant: UIAbsoluteData? = null
        private set

    var charTyped: Char? = null
        private set
    var keyCode: Int? = null
        private set

    fun update(topUIWidget: UIWidget, config: UIStyleConfig) {
        val newX = mouse.x
        val newY = config.screenHeight.toInt() - mouse.y - 1
        dX = newX - lastX
        dY = newY - lastY
        lastX = newX
        lastY = newY
        lastDwheel = mouse.scroll

        lcmHoldTime = if(mouse.isLcmDown) lcmHoldTime + 1 else 0
        rcmHoldTime = if(mouse.isRcmDown) rcmHoldTime + 1 else 0

        lcmInstant = null
        rcmInstant = null

        lcmCurrent = null
        rcmCurrent = null

        if(lcmHoldTime == 0) lcmGrabbed = null
        if(rcmHoldTime == 0) rcmGrabbed = null

        hovered = null

        val freshUIData = topUIWidget.findMouseCurrentlyOn() ?: return

        lcmInstant = if(lcmHoldTime == 1) freshUIData else null
        rcmInstant = if(rcmHoldTime == 1) freshUIData else null

        lcmCurrent = if(lcmHoldTime > 0) freshUIData else null
        rcmCurrent = if(rcmHoldTime > 0) freshUIData else null

        if(lcmHoldTime > 0 && lcmGrabbed == null) lcmGrabbed = freshUIData
        if(rcmHoldTime > 0 && rcmGrabbed == null) rcmGrabbed = freshUIData

        if(lcmHoldTime == 1) lcmSelected = freshUIData
        if(rcmHoldTime == 1) rcmSelected = freshUIData

        hovered = freshUIData

        when(keyboard.eventState) {
            IKeyboard.EventState.RELEASE -> {
                charTyped = null
                keyCode = null
            }
            else -> {
                charTyped = keyboard.eventChar
                keyCode = keyboard.eventKey
            }
        }
    }

    private fun UIWidget.findMouseCurrentlyOn(absX: Float = 0.0f, absY: Float = 0.0f): UIAbsoluteData? {
        if(!active) return null

        if(this is IParent)
            for(child in children) {
                val maybeMouseOn = child.findMouseCurrentlyOn(absX + relX, absY + relY)
                if(maybeMouseOn != null)
                    return maybeMouseOn
            }

        if(absX + relX <= lastX && lastX <= absX + relX + width)
            if(absY + relY <= lastY && lastY <= absY + relY + height)
                if(this is IInputUpdate)
                    return UIAbsoluteData(this, absX + relX, absY + relY)

        return null
    }
}