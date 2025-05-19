package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.utils.UILayoutUtils

class UITextButton(str : String, callback : () -> Unit)
    : UIButton(callback), IParent, ILayoutPost
{
    override val children = listOf(UIText(str))

    private val textGui : UIText
        get() = children[0]

    override fun applyLayoutPost() {
        UILayoutUtils.stretchToFit(this)
        UILayoutUtils.centerIn(textGui, UILayoutUtils.Rectangle(this))
    }
}