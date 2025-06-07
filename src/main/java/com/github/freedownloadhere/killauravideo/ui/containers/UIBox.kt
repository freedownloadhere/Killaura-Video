package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBasicDraw
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMovable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

abstract class UIBox(config: UIConfig): UI(config), ILayoutPost, IParent, IDrawable, IMovable, IPadded
{
    override var padding: Double = config.padding
    override var canBeMoved: Boolean = false
    override var hidden: Boolean = false

    override fun renderCallback(renderer: Renderer) = uiBasicDraw(renderer)
}