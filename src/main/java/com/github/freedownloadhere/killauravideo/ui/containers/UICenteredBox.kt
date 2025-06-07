package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBasicDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum

class UICenteredBox
    : UIBox(),
    IUniqueParent,
    ILayoutPost
{
    override lateinit var child: UI

    override fun layoutPostCallback() = uiCenterBoxLayout()
}