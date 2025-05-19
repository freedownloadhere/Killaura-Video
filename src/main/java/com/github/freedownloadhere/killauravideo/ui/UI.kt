package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.utils.UISemanticSize
import java.util.LinkedList
import java.util.Queue
import kotlin.reflect.KMutableProperty0

abstract class UI(
    posX: UISemanticSize,
    posY: UISemanticSize,
    width: UISemanticSize,
    height: UISemanticSize
) {
    enum class SizeProperty { POS_X, POS_Y, WIDTH, HEIGHT }
    private val propertyMap = mapOf(
        SizeProperty.POS_X to posX,
        SizeProperty.POS_Y to posY,
        SizeProperty.WIDTH to width,
        SizeProperty.HEIGHT to height,
    )

    internal var x = 0.0
    internal var y = 0.0
    internal var w = 1.0
    internal var h = 1.0

    var toggled = true
        private set

    open fun update(deltaTime : Long) {
        if(!toggled) return
        // maybe dont every frame
        if(this is IDrawable)
            draw()
        if(this is IParent)
            for(child in children)
                child.update(deltaTime)
    }

    open fun toggle() {
        toggled = !toggled
        if(this !is IParent)
            return

        if(toggled)
            for(child in children)
                child.enable()
        else
            for(child in children)
                child.disable()
    }

    open fun enable() {
        toggled = true
        if(this is IParent)
            for(child in children)
                child.enable()
    }

    open fun disable() {
        toggled = false
        if(this is IParent)
            for(child in children)
                child.disable()
    }
}