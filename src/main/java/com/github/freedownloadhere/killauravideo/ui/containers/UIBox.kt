package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent

abstract class UIBox: UI(), ILayoutPost, IParent
{
    var padded = true

    override fun applyLayoutPost() {
        stretchToFit()
    }

    protected abstract fun stretchToFit()
}