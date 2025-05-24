package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.ISpecialTranslate
import kotlin.math.max
import kotlin.math.min

/**
 * All `LayoutUtils` function calls begin with a `gui` parameter.
 *
 * This is the GUI that will be affected (or whose children will be affected) in the operation.
 */
object LayoutUtils {
    class Rectangle(
        var x1 : Double = 0.0,
        var y1 : Double = 0.0,
        var x2 : Double = 0.0,
        var y2 : Double = 0.0
    ) {
        constructor(gui : UI) : this(gui.relX, gui.relY, gui.relX + gui.width, gui.relY + gui.height)

        val w : Double
            get() = x2 - x1
        val h : Double
            get() = y2 - y1
        val centerX : Double
            get() = 0.5 * (x1 + x2)
        val centerY : Double
            get() = 0.5 * (y1 + y2)

        companion object {
            val wholeScreen : Rectangle
                get() = Rectangle(0.0, 0.0, GlobalManager.core!!.width.toDouble(), GlobalManager.core!!.height.toDouble())
        }

        fun scale(scaleMult : Double) : Rectangle {
            val dx = (centerX - x1) * (scaleMult - 1.0)
            val dy = (centerY - y1) * (scaleMult - 1.0)
            x1 -= dx
            y1 -= dy
            x2 += dx
            y2 += dy
            return this
        }
    }

    fun <T> list(gui : T, xSscale : Double, ySscale : Double, startH : Double = gui.relY) : Double
    where T : UI, T : IParent {
        val xS = xSscale * gui.width
        val yS = ySscale * gui.height

        var finalH = startH + yS
        for(child in gui.children) {
            setPosition(child, gui.relX + xS, finalH)
            finalH += child.height + yS
        }

        return finalH - startH
    }

    fun setAspectRatio(gui : UI, aspectRatio : Double) {
        gui.width = aspectRatio
        gui.height = 1.0
    }

    fun scaleIn(gui : UI, rect : Rectangle, paddingMult : Double = 1.0) {
        val sf = if(gui.height * (rect.w / gui.width) > rect.h) rect.h / gui.height else rect.w / gui.width
        scale(gui, sf * paddingMult)
    }

    fun scaleHeightTo(gui : UI, newH : Double) {
        scale(gui, newH / gui.height)
    }

    fun scaleWidthTo(gui : UI, newW : Double) {
        scale(gui, newW / gui.width)
    }

    fun centerIn(gui : UI, rect : Rectangle) {
        setPosition(gui, rect.centerX - 0.5 * gui.width, rect.centerY - 0.5 * gui.height)
    }

    fun <T> stretchToFit(gui : T, scaleMult : Double = 1.0)
    where T : UI, T : IParent {
        stretchToFitWidth(gui, scaleMult)
        stretchToFitHeight(gui, scaleMult)
    }

    private fun <T> stretchToFitWidth(gui : T, scaleMult : Double = 1.0)
    where T : UI, T : IParent {
        val rect = Rectangle(
            x1 = Double.POSITIVE_INFINITY,
            x2 = Double.NEGATIVE_INFINITY
        )

        for(child in gui.children) {
            rect.x1 = min(rect.x1, child.relX)
            rect.x2 = max(rect.x2, child.relX + child.width)
        }

        rect.scale(scaleMult)

        gui.relX = rect.x1
        gui.width = rect.x2 - rect.x1
    }

    fun <T> stretchToFitHeight(gui : T, scaleMult : Double = 1.0)
    where T : UI, T : IParent {
        val rect = Rectangle(
            y1 = Double.POSITIVE_INFINITY,
            y2 = Double.NEGATIVE_INFINITY
        )

        for(child in gui.children) {
            rect.y1 = min(rect.y1, child.relY)
            rect.y2 = max(rect.y2, child.relY + child.height)
        }

        rect.scale(scaleMult)

        gui.relY = rect.y1
        gui.height = rect.y2 - rect.y1
    }

    private fun scale(gui : UI, scaleMult : Double) {
        gui.width *= scaleMult
        gui.height *= scaleMult
    }

    private fun setPosition(gui : UI, newX : Double, newY : Double) {
        translate(gui, newX - gui.relX, newY - gui.relY)
    }

    private fun translate(gui : UI, dx : Double, dy : Double) {
        if(gui is ISpecialTranslate)
            gui.doSpecialTranslate(dx, dy)
        gui.relX += dx
        gui.relY += dy
        if(gui is IParent)
            for(child in gui.children)
                translate(child, dx, dy)
    }
}