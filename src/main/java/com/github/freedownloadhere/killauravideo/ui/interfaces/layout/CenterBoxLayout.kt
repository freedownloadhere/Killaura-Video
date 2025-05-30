package com.github.freedownloadhere.killauravideo.ui.interfaces.layout

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import kotlin.math.max

class CenterBoxLayout<T>: LayoutStrategy<T>() where T: UI, T: IUniqueParent {
    override fun applyFor(ui: T) {
        with(ui) {
            val pad = if (this is IPadded) padding else 0.0
            width = max(width, child.width + pad)
            height = max(height, child.height + pad)
            child.relX = 0.5 * (width - child.width)
            child.relY = 0.5 * (height - child.height)
        }
    }
}