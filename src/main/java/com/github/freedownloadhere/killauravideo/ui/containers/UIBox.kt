package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

abstract class UIBox(config: UIStyleConfig): UI(config), ILayoutPost, IParent, IDrawable, IPadded, IMouseEvent
{
    override var padding: Float = config.padding
    override var hidden: Boolean = false
    var baseColor: UIColorEnum = UIColorEnum.BOX_TERNARY
    var canBeMoved: Boolean = false

    override fun renderCallback(info: UINewRenderer.RenderInfo) = uiBoxDraw(info, baseColor)

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(canBeMoved && mouseInfo.lcmGrabbed?.ui == this) {
            relX += mouseInfo.dX
            relY += mouseInfo.dY
        }
    }
}