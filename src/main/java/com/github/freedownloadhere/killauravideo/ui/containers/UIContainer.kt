package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParentExtendable

abstract class UIContainer(
    protected val padded: Boolean = true
): UI(), ILayoutPost, IParentExtendable
{
    override val children = mutableListOf<UI>()

    protected abstract fun stretchToFit()
}