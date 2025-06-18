package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParentExtendable
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

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