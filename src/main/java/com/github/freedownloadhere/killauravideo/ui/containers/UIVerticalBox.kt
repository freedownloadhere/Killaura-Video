package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParentExtendable
import kotlin.math.max

class UIVerticalBox: UIBox(), IParentExtendable
{
    enum class Placement { TOP, BOTTOM }
    private var placementState = Placement.TOP

    private val top = mutableListOf<UI>()
    private val bottom = mutableListOf<UI>()

    override val children: Sequence<UI>
        get() = top.asSequence() + bottom.asSequence()

    fun onTop(block: UIVerticalBox.()->Unit) {
        placementState = Placement.TOP
        block()
    }

    fun onBottom(block: UIVerticalBox.()->Unit) {
        placementState = Placement.BOTTOM
        block()
    }

    override fun <T : UI> child(ui: T, init: T.() -> Unit): T {
        ui.init()
        when(placementState) {
            Placement.TOP -> top.add(ui)
            Placement.BOTTOM -> bottom.add(ui)
        }
        return ui
    }

    override fun applyLayoutPost() {
        super.applyLayoutPost()
        val pad = if(padded) GlobalManager.core!!.config.padding else 0.0
        var topY = pad
        var bottomY = height - pad
        for(child in top) {
            child.relY = topY
            topY += child.width
            child.relX = 0.5 * (width - child.width)
        }
        for(child in bottom) {
            bottomY -= child.height
            child.relY = bottomY
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