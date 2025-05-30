package com.github.freedownloadhere.killauravideo.ui.interfaces.layout

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface ILayoutPre : ILayout {
    val preLayoutStrategy: LayoutStrategy<out UI>
    fun applyLayoutPre()
}