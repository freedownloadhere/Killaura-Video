package com.github.freedownloadhere.killauravideo.ui.interfaces.layout

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface ILayoutPost: ILayout {
    val postLayoutStrategy: LayoutStrategy<out UI>
    fun applyLayoutPost()
}