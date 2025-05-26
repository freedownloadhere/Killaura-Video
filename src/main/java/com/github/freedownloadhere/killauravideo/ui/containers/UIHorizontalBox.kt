package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParentExtendable
import kotlin.math.max

class UIHorizontalBox: UIBox(), IParentExtendable
{
    enum class Placement { LEFT, RIGHT }
    private var placementState = Placement.LEFT

    private val left = mutableListOf<UI>()
    private val right = mutableListOf<UI>()

    override val children: Sequence<UI>
        get() = left.asSequence() + right.asSequence()

    fun onLeft(block: UIHorizontalBox.()->Unit) {
        placementState = Placement.LEFT
        block()
    }

    fun onRight(block: UIHorizontalBox.()->Unit) {
        placementState = Placement.RIGHT
        block()
    }

    override fun <T : UI> child(ui: T, init: T.() -> Unit): T {
        ui.init()
        when(placementState) {
            Placement.LEFT -> left.add(ui)
            Placement.RIGHT -> right.add(ui)
        }
        return ui
    }

    override fun applyLayoutPost() {
        stretchSelf()
        val pad = if(padded) GlobalManager.core!!.config.padding else 0.0
        var leftX = pad
        var rightX = width - pad
        for(child in left) {
            child.relX = leftX
            leftX += child.width
            child.relY = 0.5 * (height - child.height)
        }
        for(child in right) {
            rightX -= child.width
            child.relX = rightX
            child.relY = 0.5 * (height - child.height)
        }
    }

    private fun stretchSelf() {
        var minWidth = 0.0
        var minHeight = 0.0
        for(child in children) {
            minWidth += child.width
            minHeight = max(minHeight, child.height)
        }
        if(padded) {
            val pad = 2.0 * GlobalManager.core!!.config.padding
            minWidth += pad
            minHeight += pad
        }
        width = max(width, minWidth)
        height = max(height, minHeight)
    }
}