package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.*
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent

class InteractionManager(private val mouseInfo: MouseInfo, private val ui: UI) {
    var focused: UI? = null
        private set
    private var hovered: UI? = null
    private var lastMouseOn: UI? = null
    private var moveLock: UI? = null

    fun handleMouseInput() {
        lastMouseOn = findMouseOn(ui, 0.0, 0.0)
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
        if(!mouseInfo.mouseIsClicked) return
        focused = lastMouseOn
        if(focused == null) return
        if(focused is IClickable)
            (focused as IClickable).onClick(mouseInfo.mouseButtonMask)
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
        if(!mouseInfo.mouseIsDown) {
            moveLock = null
            return
        }
        if(moveLock == null) return
        moveLock!!.relX += mouseInfo.mouseDX
        moveLock!!.relY += mouseInfo.mouseDY
    }

    private fun findMouseOn(u: UI, absX: Double, absY: Double): UI? {
        if(!u.toggled)
            return null

        if(u is IParent)
            for(v in u.children) {
                val gui = findMouseOn(v, absX + v.relX, absY + v.relY)
                if(gui != null)
                    return gui
            }

        val x = mouseInfo.lastMouseX
        val y = mouseInfo.lastMouseY
        if(absX <= x && x <= absX + u.width)
            if(absY <= y && y <= absY + u.height)
                if(u is IInteractable)
                    return u

        return null
    }
}