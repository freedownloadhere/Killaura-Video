package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import kotlin.math.max

fun <T> T.uiCenterBoxLayout() where T: UI, T: IUniqueParent {
    val pad = if (this is IPadded) padding else 0.0
    width = max(width, child.width + pad)
    height = max(height, child.height + pad)
    child.relX = 0.5 * (width - child.width)
    child.relY = 0.5 * (height - child.height)
}