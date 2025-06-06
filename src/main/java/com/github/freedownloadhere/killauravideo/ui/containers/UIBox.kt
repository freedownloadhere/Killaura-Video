package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBasicDraw
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IHoverable
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMovable
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum

abstract class UIBox
    : UI(),
    ILayoutPost,
    IParent,
    IDrawable,
    IMovable,
    IPadded,
    IHoverable
{
    override var padding: Double = GlobalManager.core!!.config.padding
    override var canBeMoved: Boolean = false
    override var hidden: Boolean = false
    override var baseColor: UIColorEnum = UIColorEnum.NEUTRAL

    override fun draw() = uiBasicDraw()
    override fun onHoverStart() { baseColor = UIColorEnum.NEUTRAL_LIGHT }
    override fun onHoverStop() { baseColor = UIColorEnum.NEUTRAL }
}