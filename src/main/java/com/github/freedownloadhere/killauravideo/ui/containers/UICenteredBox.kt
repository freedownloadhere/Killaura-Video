package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.CenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.LayoutStrategy

class UICenteredBox
    : UIBox(),
    IUniqueParent,
    ILayoutPost
{
    override lateinit var child: UI

    override val postLayoutStrategy = CenterBoxLayout<UICenteredBox>()

    override fun applyLayoutPost() {
        postLayoutStrategy.applyFor(this)
    }
}