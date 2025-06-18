package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIIcon

class UICheckbox(config: UIStyleConfig): UI(config), IUniqueParent<UIIcon>, IMouseEvent, ILayoutPost, IPadded
{
    var checked: Boolean = false
        set(value) {
            field = value
            child.hidden = !value
        }
    var onCheck: () -> Unit = { }
    override val child: UIIcon = UIIcon(config)

    override var padding: Float = config.padding

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmInstant?.ui == this) {
            checked = !checked
            onCheck()
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, UIColorEnum.BOX_SECONDARY)
}