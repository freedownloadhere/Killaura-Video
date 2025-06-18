package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParentExtendable
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import kotlin.math.max

class UIWidgetHorizontalBox(config: UIStyleConfig) : UIWidgetBox(config), IParentExtendable
{
    enum class Placement { LEFT, RIGHT }
    private var placementState = Placement.LEFT

    private val left = mutableListOf<UIWidget>()
    private val right = mutableListOf<UIWidget>()

    override val children: Sequence<UIWidget>
        get() = left.asSequence() + right.asSequence()

    fun placeLeft(block: () -> Unit = { }) {
        placementState = Placement.LEFT
        block()
    }

    fun placeRight(block: () -> Unit = { }) {
        placementState = Placement.RIGHT
        block()
    }

    override fun addChild(uiWidget: UIWidget) {
        when(placementState) {
            Placement.LEFT -> left += uiWidget
            Placement.RIGHT -> right += uiWidget
        }
    }

    override fun layoutPostCallback() {
        stretchSelf()
        val pad = if(enablePadding) config.padding else 0.0f
        var leftX = pad
        var rightX = width - pad
        for(child in left) {
            child.relX = leftX
            leftX += child.width
            child.relY = 0.5f * (height - child.height)
        }
        for(child in right) {
            rightX -= child.width
            child.relX = rightX
            child.relY = 0.5f * (height - child.height)
        }
    }

    private fun stretchSelf() {
        val pad = if(enablePadding) config.padding else 0.0f
        var minWidth = 0.0f
        var minHeight = 0.0f
        for(child in children) {
            minWidth += child.width
            minHeight = max(minHeight, child.height)
        }
        minWidth += 2.0f * pad
        minHeight += 2.0f * pad
        width = max(width, minWidth)
        height = max(height, minHeight)
    }
}