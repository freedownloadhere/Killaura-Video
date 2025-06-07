package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIIcon
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

class UICheckbox(config: UIConfig) : UI(config), IUniqueParent<UIIcon>, IDrawable, IClickable, ILayoutPost, IPadded
{
    var checked: Boolean = false
        set(value) {
            field = value
            child.hidden = !value
        }
    var onCheck: () -> Unit = { }
    override val child: UIIcon = UIIcon(config)
    override var hidden: Boolean = false
    override var padding: Double = config.padding

    override fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        checked = !checked
        onCheck()
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(renderer: Renderer) = uiBoxDraw(renderer, UIColorEnum.BOX_SECONDARY)
}