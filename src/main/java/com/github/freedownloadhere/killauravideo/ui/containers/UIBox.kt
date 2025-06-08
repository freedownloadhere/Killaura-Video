package com.github.freedownloadhere.killauravideo.ui.containers

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IGrabbable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

abstract class UIBox(config: UIConfig): UI(config), ILayoutPost, IParent, IDrawable, IGrabbable, IPadded
{
    override var padding: Double = config.padding
    override var hidden: Boolean = false
    var baseColor: UIColorEnum = UIColorEnum.BOX_TERNARY
    var canBeMoved: Boolean = false

    override fun renderCallback(renderer: Renderer) = uiBoxDraw(renderer, baseColor)

    override fun grabCallback(mouseRelDX: Double, mouseRelDY: Double) {
        if(canBeMoved) {
            relX += mouseRelDX
            relY += mouseRelDY
        }
    }
}