package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPre
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.utils.UILayoutUtils

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
        UILayoutUtils.scaleIn(this, UILayoutUtils.Rectangle.wholeScreen, 0.9)

        UILayoutUtils.scaleHeightTo(titleBar, 0.1 * h)
        titleBar.w = w

        UILayoutUtils.scaleHeightTo(contents, 0.9 * h)
        contents.w = w

        UILayoutUtils.list(this, 0.0, 0.0)

        UILayoutUtils.centerIn(this, UILayoutUtils.Rectangle.wholeScreen)
    }
}