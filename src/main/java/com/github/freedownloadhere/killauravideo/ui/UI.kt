package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.utils.UISemanticSize
import com.github.freedownloadhere.killauravideo.ui.utils.UISemanticSize.ChildrenSum.applyProperty
import java.util.LinkedList
import java.util.Queue
import kotlin.reflect.KMutableProperty0

abstract class UI(
    posX: UISemanticSize,
    posY: UISemanticSize,
    width: UISemanticSize,
    height: UISemanticSize
) {
    data class Property(val semantic: UISemanticSize, var number: Double)
    enum class EnumProperty { POS_X, POS_Y, WIDTH, HEIGHT }

    internal val parent: UI? = null

    private val posXProperty = Property(posX, 0.0)
    private val posYProperty = Property(posY, 0.0)
    private val widthProperty = Property(width, 0.0)
    private val heightProperty = Property(height, 0.0)

    var x: Double
        get() = posXProperty.number
        set(value) { posXProperty.number = value }

    var y: Double
        get() = posYProperty.number
        set(value) { posYProperty.number = value }

    var w: Double
        get() = widthProperty.number
        set(value) { widthProperty.number = value }

    var h: Double
        get() = heightProperty.number
        set(value) { heightProperty.number = value }

    var toggled = true
        private set

    fun getProperty(enumProperty: EnumProperty): Property = when(enumProperty) {
        EnumProperty.POS_X -> posXProperty
        EnumProperty.POS_Y -> posYProperty
        EnumProperty.WIDTH -> widthProperty
        EnumProperty.HEIGHT -> heightProperty
    }

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

    fun applyLayout() {
        for(enumProperty in UI.EnumProperty.entries)
            applyLayoutForProperty(enumProperty)
    }

    private fun applyLayoutForProperty(enumProperty: EnumProperty) {
        val applyOrder = getProperty(enumProperty).semantic.applyOrder

        if(applyOrder == UISemanticSize.ApplyOrder.BEFORE)
            applySemantic(enumProperty)
        if(this is IParent)
            for(child in children)
                child.applyLayoutForProperty(enumProperty)
        if(applyOrder == UISemanticSize.ApplyOrder.AFTER)
            applySemantic(enumProperty)
    }

    private fun applySemantic(enumProperty: EnumProperty) {
        val property = getProperty(enumProperty)
        with(property.semantic) {
            this@UI.applyProperty(enumProperty)
        }
    }
}