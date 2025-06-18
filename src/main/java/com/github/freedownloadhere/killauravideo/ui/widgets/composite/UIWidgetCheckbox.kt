package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetIcon

class UIWidgetCheckbox(config: UIStyleConfig): UIWidget(config), IUniqueParent<UIWidgetIcon>, IMouseEvent, ILayoutPost, IPadded
{
    var checked: Boolean = false
        set(value) {
            field = value
            child.hidden = !value
        }
    var onCheck: () -> Unit = { }
    override val child: UIWidgetIcon = UIWidgetIcon(config)

    override var enablePadding: Boolean = true

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmInstant?.uiWidget == this) {
            checked = !checked
            onCheck()
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, config.colorBoxSecondary)
}