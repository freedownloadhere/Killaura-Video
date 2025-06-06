package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParentExtendable
import kotlin.math.max

class UIVerticalBox: UIBox(), IParentExtendable
{
    enum class Placement { TOP, BOTTOM }
    private var placementState = Placement.TOP

    private val top = mutableListOf<UI>()
    private val bottom = mutableListOf<UI>()

    var stretchChildren = true

    var spacing: Double = 0.0

    override val children: Sequence<UI>
        get() = top.asSequence() + bottom.asSequence()

    override fun addChild(ui: UI) {
        when(placementState) {
            Placement.TOP -> top += ui
            Placement.BOTTOM -> bottom += ui
        }
    }

    override fun applyLayoutPost() {
        stretchSelf()
        var topY = padding
        var bottomY = height - padding
        if(stretchChildren) for(child in children) {
            child.width = width - 2.0 * padding
        }
        for(child in top) {
            child.relY = topY
            topY += (child.height + spacing)
            child.relX = 0.5 * (width - child.width)
        }
        for(child in bottom) {
            bottomY -= (child.height + spacing)
            child.relY = bottomY
            child.relX = 0.5 * (width - child.width)
        }
    }

    private fun stretchSelf() {
        var minWidth = 0.0
        var minHeight = 0.0
        for(child in children) {
            minHeight += child.height
            minWidth = max(minWidth, child.width)
        }
        minHeight += 2.0 * padding + (top.size + bottom.size - 1) * spacing
        minWidth += 2.0 * padding
        height = max(height, minHeight)
        width = max(width, minWidth)
    }
}