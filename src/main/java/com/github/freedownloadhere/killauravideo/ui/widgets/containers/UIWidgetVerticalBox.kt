package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParentExtendable
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import kotlin.math.max

class UIWidgetVerticalBox(config: UIStyleConfig) : UIWidgetBox(config), IParentExtendable
{
    enum class Placement { TOP, BOTTOM }
    private var placementState = Placement.TOP

    private val top = mutableListOf<UIWidget>()
    private val bottom = mutableListOf<UIWidget>()

    var stretchChildren: Boolean = true

    var spacing: Float = 0.0f

    override val children: Sequence<UIWidget>
        get() = top.asSequence() + bottom.asSequence()

    override fun addChild(uiWidget: UIWidget) {
        when(placementState) {
            Placement.TOP -> top += uiWidget
            Placement.BOTTOM -> bottom += uiWidget
        }
    }

    override fun layoutPostCallback() {
        stretchSelf()
        val pad = if(enablePadding) config.padding else 0.0f
        var topY = pad
        var bottomY = height - pad
        if(stretchChildren) for(child in children) {
            child.width = width - 2.0f * pad
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
        val pad = if(enablePadding) config.padding else 0.0f
        var minWidth = 0.0f
        var minHeight = 0.0f
        for(child in children) {
            minHeight += child.height
            minWidth = max(minWidth, child.width)
        }
        minHeight += 2.0f * pad + (top.size + bottom.size - 1) * spacing
        minWidth += 2.0f * pad
        height = max(height, minHeight)
        width = max(width, minWidth)
    }
}