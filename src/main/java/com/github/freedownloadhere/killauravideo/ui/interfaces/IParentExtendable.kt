package com.github.freedownloadhere.killauravideo.ui.interfaces

import com.github.freedownloadhere.killauravideo.ui.UI

interface IParentExtendable : IParent {
    override val children: MutableList<UI>

    fun addChild(child : UI) {
        children.add(child)
    }
}