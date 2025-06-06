package com.github.freedownloadhere.killauravideo.ui.containers

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

    fun placeLeft(block: () -> Unit = { }) {
        placementState = Placement.LEFT
        block()
    }

    fun placeRight(block: () -> Unit = { }) {
        placementState = Placement.RIGHT
        block()
    }

    override fun addChild(ui: UI) {
        when(placementState) {
            Placement.LEFT -> left += ui
            Placement.RIGHT -> right += ui
        }
    }

    override fun applyLayoutPost() {
        stretchSelf()
        var leftX = padding
        var rightX = width - padding
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
        minWidth += 2.0 * padding
        minHeight += 2.0 * padding
        width = max(width, minWidth)
        height = max(height, minHeight)
    }
}