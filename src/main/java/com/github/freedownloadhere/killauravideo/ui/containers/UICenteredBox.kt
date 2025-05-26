package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.IUniqueParent
import kotlin.math.max

open class UICenteredBox<T: UI>(
    override val child: T
) : UIBox(), IUniqueParent<T>
{
    override fun applyLayoutPost() {
        super.applyLayoutPost()
        child.relX = 0.5 * (width - child.width)
        child.relY = 0.5 * (height - child.height)
    }

    override fun stretchToFit() {
        val pad = if(padded) 2.0 * GlobalManager.core!!.config.padding else 0.0
        width = max(width, child.width + pad)
        height = max(height, child.height + pad)
    }
}