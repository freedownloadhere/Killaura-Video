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
        onClick()
        onHold()
        onScroll()
        onMove()
    }

    fun handleKeyTyped(typedChar : Char, keyCode : Int) {
        if(focused != null && focused is ITypable)
            (focused as ITypable).keyTypedCallback(typedChar, keyCode)
    }

    private fun onHover() {
        if(lastMouseOn != hovered) {
            if(hovered != null && hovered is IHoverable)
                (hovered as IHoverable).hoverStopCallback()
            if(lastMouseOn != null && lastMouseOn is IHoverable)
                (lastMouseOn as IHoverable).hoverStartCallback()
            hovered = lastMouseOn
        }
    }

    private fun onClick() {
        if(!mouseInfo.isClicked) return
        focused = lastMouseOn
        if(focused == null) return
        if(focused is IClickable)
            (focused as IClickable).clickCallback(
                mouseInfo.buttonmask,
                mouseInfo.lastX.toDouble() - lastMouseUIAbsX,
                mouseInfo.lastY.toDouble() - lastMouseUIAbsY
            )
        else if(focused is IMovable && moveLock == null)
            moveLock = focused
    }

    private fun onHold() {
        if(!mouseInfo.isHeldDown) return
        if(focused != lastMouseOn) return
        if(focused is IClickHoldable)
            (focused as IClickHoldable).clickHoldCallback(
                mouseInfo.buttonmask,
                mouseInfo.lastX.toDouble() - lastMouseUIAbsX,
                mouseInfo.lastY.toDouble() - lastMouseUIAbsY
            )
    }

    private fun onScroll() {
        if(mouseInfo.lastDwheel != 0) {
            val d = mouseInfo.lastDwheel / mouseInfo.scrollSens
            if(focused != null && focused is IScrollable)
                (focused as IScrollable).scrollCallback(d)
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
        if(!active)
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