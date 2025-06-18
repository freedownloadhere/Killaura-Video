package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParentExtendable
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class UIFreeBox(config: UIStyleConfig) : UIBox(config), IParentExtendable
{
    private val childrenList = mutableListOf<UI>()

    override val children: Sequence<UI>
        get() = childrenList.asSequence()

    override fun addChild(ui: UI) {
        childrenList += ui
    }

    override fun layoutPostCallback() {

    }
}