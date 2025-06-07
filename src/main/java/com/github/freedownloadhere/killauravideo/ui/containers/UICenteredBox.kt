package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

open class UICenteredBox<T>(config: UIConfig) : UIBox(config), IUniqueParent<T>, ILayoutPost where T: UI
{
    override lateinit var child: T

    override fun layoutPostCallback() = uiCenterBoxLayout()
}