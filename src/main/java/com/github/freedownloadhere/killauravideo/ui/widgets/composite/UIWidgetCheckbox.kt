package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IInputUpdate
import com.github.freedownloadhere.killauravideo.ui.core.io.InputData
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetIcon

class UIWidgetCheckbox(config: UIStyleConfig): UIWidget(config), IUniqueParent<UIWidgetIcon>, IInputUpdate, ILayoutPost, IPadded
{
    var checked: Boolean = false
        set(value) {
            field = value
            child.hidden = !value
        }
    var onCheck: () -> Unit = { }
    override val child: UIWidgetIcon = UIWidgetIcon(config)

    override var enablePadding: Boolean = true

    override fun inputUpdateCallback(io: InputData) {
        if(io.lcmInstant?.uiWidget == this) {
            checked = !checked
            onCheck()
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, config.colorBoxSecondary)
}