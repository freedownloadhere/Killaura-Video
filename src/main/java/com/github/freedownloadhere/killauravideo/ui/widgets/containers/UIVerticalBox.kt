package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParentExtendable
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI
import kotlin.math.max

class UIVerticalBox(config: UIStyleConfig) : UIBox(config), IParentExtendable
{
    enum class Placement { TOP, BOTTOM }
    private var placementState = Placement.TOP

    private val top = mutableListOf<UI>()
    private val bottom = mutableListOf<UI>()

    var stretchChildren: Boolean = true

    var spacing: Float = 0.0f

    override val children: Sequence<UI>
        get() = top.asSequence() + bottom.asSequence()

    override fun addChild(ui: UI) {
        when(placementState) {
            Placement.TOP -> top += ui
            Placement.BOTTOM -> bottom += ui
        }
    }

    override fun layoutPostCallback() {
        stretchSelf()
        var topY = padding
        var bottomY = height - padding
        if(stretchChildren) for(child in children) {
            child.width = width - 2.0f * padding
        }
        for(child in top) {
            child.relY = topY
            topY += (child.height + spacing)
            child.relX = 0.5f * (width - child.width)
        }
        for(child in bottom) {
            bottomY -= (child.height + spacing)
            child.relY = bottomY
            child.relX = 0.5f * (width - child.width)
        }
    }

    private fun stretchSelf() {
        var minWidth = 0.0f
        var minHeight = 0.0f
        for(child in children) {
            minHeight += child.height
            minWidth = max(minWidth, child.width)
        }
        minHeight += 2.0f * padding + (top.size + bottom.size - 1) * spacing
        minWidth += 2.0f * padding
        height = max(height, minHeight)
        width = max(width, minWidth)
    }
}