package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMovable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

abstract class UIBox(config: UIConfig): UI(config), ILayoutPost, IParent, IDrawable, IMovable, IPadded
{
    override var padding: Double = config.padding
    override var canBeMoved: Boolean = false
    override var hidden: Boolean = false
    var baseColor: UIColorEnum = UIColorEnum.BOX_TERNARY

    override fun renderCallback(renderer: Renderer) = uiBoxDraw(renderer, baseColor)
}