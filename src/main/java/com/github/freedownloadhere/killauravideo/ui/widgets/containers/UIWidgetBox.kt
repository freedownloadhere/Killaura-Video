package com.github.freedownloadhere.killauravideo.ui.widgets.containers

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IInputUpdate
import com.github.freedownloadhere.killauravideo.ui.core.io.InputData
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

abstract class UIWidgetBox(config: UIStyleConfig): UIWidget(config), ILayoutPost, IParent, IPadded, IInputUpdate
{
    override var enablePadding: Boolean = true
    var canBeMoved: Boolean = false

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, config.colorBoxTernary)

    override fun inputUpdateCallback(io: InputData) {
        if(canBeMoved && io.lcmGrabbed?.uiWidget == this) {
            relX += io.dX
            relY += io.dY
        }
    }
}