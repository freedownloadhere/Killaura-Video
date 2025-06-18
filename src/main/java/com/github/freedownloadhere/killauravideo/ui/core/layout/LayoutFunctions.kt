package com.github.freedownloadhere.killauravideo.ui.core.layout

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI
import kotlin.math.max

fun <T> T.uiCenterBoxLayout() where T: UI, T: IUniqueParent<*> {
    val pad = if (this is IPadded) padding else 0.0f
    width = max(width, child.width + pad)
    height = max(height, child.height + pad)
    child.relX = 0.5f * (width - child.width)
    child.relY = 0.5f * (height - child.height)
}