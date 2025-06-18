package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import java.awt.Color

abstract class UIWidgetBox(config: UIStyleConfig): UIWidget(config), ILayoutPost, IParent, IPadded, IMouseEvent
{
    override var enablePadding: Boolean = true
    var canBeMoved: Boolean = false

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, config.colorBoxTernary)

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(canBeMoved && mouseInfo.lcmGrabbed?.uiWidget == this) {
            relX += mouseInfo.dX
            relY += mouseInfo.dY
        }
    }
}