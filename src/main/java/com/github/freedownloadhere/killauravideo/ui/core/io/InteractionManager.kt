package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.*
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent

class InteractionManager(private val inputManager: InputManager, private val rootGui : UI?) {
    var focused : UI? = null
        private set
    private var hovered : UI? = null
    private var lastMouseOn : UI? = null

    fun handleMouseInput() {
        if(rootGui == null) return
        lastMouseOn = findMouseOn(rootGui, 0.0, 0.0)
        onHover()
        onMouseClick()
        onScroll()
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
        if(inputManager.mouseIsClicked) {
            focused = lastMouseOn
            if(focused != null && focused is IClickable)
                (focused as IClickable).onClick(inputManager.mouseButtonMask)
        }
    }

    private fun onScroll() {
        if(inputManager.lastDwheel != 0) {
            val d = inputManager.lastDwheel / inputManager.scrollSens
            if(focused != null && focused is IScrollable)
                (focused as IScrollable).onScroll(d)
        }
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

        val x = inputManager.lastMouseX
        val y = inputManager.lastMouseY
        if(absX <= x && x <= absX + u.width)
            if(absY <= y && y <= absY + u.height)
                if(u is IInteractable)
                    return u

        return null
    }
}