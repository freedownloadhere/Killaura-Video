package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIIcon
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class UICheckbox(config: UIStyleConfig): UI(config), IUniqueParent<UIIcon>, IDrawable, IMouseEvent, ILayoutPost, IPadded
{
    var checked: Boolean = false
        set(value) {
            field = value
            child.hidden = !value
        }
    var onCheck: () -> Unit = { }
    override val child: UIIcon = UIIcon(config)
    override var hidden: Boolean = false
    override var padding: Float = config.padding

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmInstant?.ui == this) {
            checked = !checked
            onCheck()
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(info: UINewRenderer.RenderInfo) = uiBoxDraw(info, UIColorEnum.BOX_SECONDARY)
}