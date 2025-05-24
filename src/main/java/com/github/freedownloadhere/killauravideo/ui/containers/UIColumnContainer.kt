package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.UI
import kotlin.math.max

class UIColumnContainer: UIContainer()
{
    private val atTheBottom = mutableSetOf<UI>()

    fun addChild(child: UI, anchorBottom: Boolean = false) {
        addChild(child)
        if(anchorBottom) atTheBottom.add(child)
    }

    override fun applyLayoutPost() {
        stretchToFit()
        val pad = if(padded) GlobalManager.core!!.config.padding else 0.0
        var topY = pad
        var bottomY = height - pad
        for(child in children) {
            if(atTheBottom.contains(child)) {
                bottomY -= child.height
                child.relY = bottomY
            } else {
                child.relY = topY
                topY += child.width
            }
            child.relX = 0.5 * (width - child.width)
        }
    }

    override fun stretchToFit() {
        var minWidth = 0.0
        var minHeight = 0.0
        for(child in children) {
            minHeight += child.height
            minWidth = max(minWidth, child.width)
        }
        if(padded) {
            val pad = 2.0 * GlobalManager.core!!.config.padding
            minHeight += pad
            minWidth += pad
        }
        height = max(height, minHeight)
        width = max(width, minWidth)
    }
}