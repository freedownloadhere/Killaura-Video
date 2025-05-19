package com.github.freedownloadhere.killauravideo.ui.utils

import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.ui.UICore
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.ISpecialTranslate
import kotlin.math.max
import kotlin.math.min

/**
 * All `LayoutUtils` function calls begin with a `gui` parameter.
 *
 * This is the GUI that will be affected (or whose children will be affected) in the operation.
 */
object UILayoutUtils {

    class Rectangle(
        var x1 : Double = 0.0,
        var y1 : Double = 0.0,
        var x2 : Double = 0.0,
        var y2 : Double = 0.0
    ) {
        constructor(gui : UI) : this(gui.x, gui.y, gui.x + gui.w, gui.y + gui.h)

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
                get() = Rectangle(0.0, 0.0, UICore.width.toDouble(), UICore.height.toDouble())
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

    fun <T> list(gui : T, xSscale : Double, ySscale : Double, startH : Double = gui.y) : Double
    where T : UI, T : IParent {
        val xS = xSscale * gui.w
        val yS = ySscale * gui.h

        var finalH = startH + yS
        for(child in gui.children) {
            setPosition(child, gui.x + xS, finalH)
            finalH += child.h + yS
        }

        return finalH - startH
    }

    fun setAspectRatio(gui : UI, aspectRatio : Double) {
        gui.w = aspectRatio
        gui.h = 1.0
    }

    fun scaleIn(gui : UI, rect : Rectangle, paddingMult : Double = 1.0) {
        val sf = if(gui.h * (rect.w / gui.w) > rect.h) rect.h / gui.h else rect.w / gui.w
        scale(gui, sf * paddingMult)
    }

    fun scaleHeightTo(gui : UI, newH : Double) {
        scale(gui, newH / gui.h)
    }

    fun scaleWidthTo(gui : UI, newW : Double) {
        scale(gui, newW / gui.w)
    }

    fun centerIn(gui : UI, rect : Rectangle) {
        setPosition(gui, rect.centerX - 0.5 * gui.w, rect.centerY - 0.5 * gui.h)
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
            rect.x1 = min(rect.x1, child.x)
            rect.x2 = max(rect.x2, child.x + child.w)
        }

        rect.scale(scaleMult)

        gui.x = rect.x1
        gui.w = rect.x2 - rect.x1
    }

    fun <T> stretchToFitHeight(gui : T, scaleMult : Double = 1.0)
    where T : UI, T : IParent {
        val rect = Rectangle(
            y1 = Double.POSITIVE_INFINITY,
            y2 = Double.NEGATIVE_INFINITY
        )

        for(child in gui.children) {
            rect.y1 = min(rect.y1, child.y)
            rect.y2 = max(rect.y2, child.y + child.h)
        }

        rect.scale(scaleMult)

        gui.y = rect.y1
        gui.h = rect.y2 - rect.y1
    }

    private fun scale(gui : UI, scaleMult : Double) {
        gui.w *= scaleMult
        gui.h *= scaleMult
    }

    private fun setPosition(gui : UI, newX : Double, newY : Double) {
        translate(gui, newX - gui.x, newY - gui.y)
    }

    private fun translate(gui : UI, dx : Double, dy : Double) {
        if(gui is ISpecialTranslate)
            gui.doSpecialTranslate(dx, dy)
        gui.x += dx
        gui.y += dy
        if(gui is IParent)
            for(child in gui.children)
                translate(child, dx, dy)
    }
}