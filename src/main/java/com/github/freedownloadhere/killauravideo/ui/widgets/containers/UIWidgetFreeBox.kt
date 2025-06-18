package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParentExtendable
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

class UIWidgetFreeBox(config: UIStyleConfig) : UIWidgetBox(config), IParentExtendable
{
    private val childrenList = mutableListOf<UIWidget>()

    override val children: Sequence<UIWidget>
        get() = childrenList.asSequence()

    override fun addChild(uiWidget: UIWidget) {
        childrenList += uiWidget
    }

    override fun layoutPostCallback() {

    }
}