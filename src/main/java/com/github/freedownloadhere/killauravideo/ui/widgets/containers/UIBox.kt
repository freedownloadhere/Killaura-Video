package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

abstract class UIBox(config: UIStyleConfig): UI(config), ILayoutPost, IParent, IPadded, IMouseEvent
{
    override var padding: Float = config.padding
    var baseColor: UIColorEnum = UIColorEnum.BOX_TERNARY
    var canBeMoved: Boolean = false

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, baseColor)

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(canBeMoved && mouseInfo.lcmGrabbed?.ui == this) {
            relX += mouseInfo.dX
            relY += mouseInfo.dY
        }
    }
}