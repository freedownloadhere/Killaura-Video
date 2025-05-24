package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.UI
import kotlin.math.max

class UILineContainer: UIContainer(padded = true)
{
    private val toTheRight = mutableSetOf<UI>()

    fun addChild(child: UI, anchorRight: Boolean = false) {
        addChild(child)
        if(anchorRight) toTheRight.add(child)
    }

    override fun applyLayoutPost() {
        stretchToFit()
        val pad = if(padded) GlobalManager.core!!.config.padding else 0.0
        var leftX = pad
        var rightX = width - pad
        for(child in children) {
            if(toTheRight.contains(child)) {
                rightX -= child.width
                child.relX = rightX
            } else {
                child.relX = leftX
                leftX += child.width
            }
            child.relY = 0.5 * (height - child.height)
        }
    }

    override fun stretchToFit() {
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