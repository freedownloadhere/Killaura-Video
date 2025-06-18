package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

open class UIWidgetCenteredBox<T>(config: UIStyleConfig) : UIWidgetBox(config), IUniqueParent<T>, ILayoutPost where T: UIWidget
{
    override lateinit var child: T

    override fun layoutPostCallback() = uiCenterBoxLayout()
}