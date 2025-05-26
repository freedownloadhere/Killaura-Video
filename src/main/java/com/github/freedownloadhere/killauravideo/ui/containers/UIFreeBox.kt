package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParentExtendable

class UIFreeBox: UIBox(), IParentExtendable
{
    private val childrenList = mutableListOf<UI>()

    override val children: Sequence<UI>
        get() = childrenList.asSequence()

    override fun draw() {
        super.draw()
    }

    override fun <T : UI> child(ui: T, init: T.() -> Unit): T {
        ui.init()
        childrenList.add(ui)
        return ui
    }

    override fun stretchToFit() { }
}