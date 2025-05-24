package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPre
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.core.LayoutUtils

class UIWindow(title : String)
    : UI(), IParent, ILayoutPre
{
    override val children = listOf<UI>(
        UITitleBar(title),
        UIListContainer()
    )

    private val titleBar : UITitleBar
        get() = children[0] as UITitleBar
    val contents : UIListContainer
        get() = children[1] as UIListContainer

    override fun applyLayoutPre() {
        LayoutUtils.scaleIn(this, LayoutUtils.Rectangle.wholeScreen, 0.9)

        LayoutUtils.scaleHeightTo(titleBar, 0.1 * height)
        titleBar.width = width

        LayoutUtils.scaleHeightTo(contents, 0.9 * height)
        contents.width = width

        LayoutUtils.list(this, 0.0, 0.0)

        LayoutUtils.centerIn(this, LayoutUtils.Rectangle.wholeScreen)
    }
}