package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.*
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent

class InteractionManager(private val mouseInfo: MouseInfo, private val topUI: UI) {
    var focused: UI? = null
        private set
    private var hovered: UI? = null
    private var lastMouseOn: UI? = null
    private var lastMouseUIAbsX: Double = 0.0
    private var lastMouseUIAbsY: Double = 0.0
    private var moveLock: UI? = null

    fun handleMouseInput() {
        val findMouseResult = topUI.findMouseOn(0.0, 0.0)
        if(findMouseResult != null) {
            val (newLastMouseOn, absX, absY) = findMouseResult
            lastMouseOn = newLastMouseOn
            lastMouseUIAbsX = absX
            lastMouseUIAbsY = absY
        } else {
            lastMouseOn = null
        }
        onHover()
        onMouseClick()
        onScroll()
        onMove()
    }

    fun handleKeyTyped(typedChar : Char, keyCode : Int) {
        if(focused != null && focused is ITypable)
            (focused as ITypable).onKeyTyped(typedChar, keyCode)
    }

    private fun onHover() {
        if(lastMouseOn != hovered) {
            if(hovered != null && hovered is IHoverable)
                (hovered as IHoverable).onHoverStop()
            if(lastMouseOn != null && lastMouseOn is IHoverable)
                (lastMouseOn as IHoverable).onHoverStart()
            hovered = lastMouseOn
        }
    }

    private fun onMouseClick() {
        if(!mouseInfo.isClicked) return
        focused = lastMouseOn
        if(focused == null) return
        if(focused is IClickable)
            (focused as IClickable).onClick(
                mouseInfo.buttonmask,
                mouseInfo.lastX.toDouble() - lastMouseUIAbsX,
                mouseInfo.lastY.toDouble() - lastMouseUIAbsY
            )
        else if(focused is IMovable && moveLock == null)
            moveLock = focused
    }

    private fun onScroll() {
        if(mouseInfo.lastDwheel != 0) {
            val d = mouseInfo.lastDwheel / mouseInfo.scrollSens
            if(focused != null && focused is IScrollable)
                (focused as IScrollable).onScroll(d)
        }
    }

    private fun onMove() {
        if(!mouseInfo.isHeldDown) {
            moveLock = null
            return
        }
        if(moveLock == null) return
        moveLock!!.relX += mouseInfo.dX
        moveLock!!.relY += mouseInfo.dY
    }

    private fun UI.findMouseOn(absX: Double, absY: Double): Triple<UI, Double, Double>? {
        if(!toggled)
            return null

        if(this is IParent)
            for(child in children) {
                val maybeClicked = child.findMouseOn(absX + relX, absY + relY)
                if(maybeClicked != null)
                    return maybeClicked
            }

        val x = mouseInfo.lastX
        val y = mouseInfo.lastY

        if(absX + relX <= x && x <= absX + relX + width)
            if(absY + relY <= y && y <= absY + relY + height)
                if(this is IInteractable)
                    return Triple(this, absX + relX, absY + relY)

        return null
    }
}