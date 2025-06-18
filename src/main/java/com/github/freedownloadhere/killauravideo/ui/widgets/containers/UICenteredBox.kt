package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

open class UICenteredBox<T>(config: UIStyleConfig) : UIBox(config), IUniqueParent<T>, ILayoutPost where T: UI
{
    override lateinit var child: T

    override fun layoutPostCallback() = uiCenterBoxLayout()
}