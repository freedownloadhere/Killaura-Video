package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetText
import java.awt.Color
import kotlin.math.max

class UIWidgetButton(config: UIStyleConfig): UIWidget(config), IUniqueParent<UIWidgetText>, ILayoutPost, IMouseEvent
{
    private val cooldown: Long = config.buttonClickCooldown
    private var cooldownLeft: Long  = 0L

    override val child: UIWidgetText = UIWidgetText(config)

    var onClick: () -> Unit = { }
    private var color: Color = config.colorBoxSecondary

    override fun update(deltaTime: Long) {
        cooldownLeft = max(0L, cooldownLeft - deltaTime)
        if(cooldownLeft == 0L) color = config.colorBoxSecondary
        super.update(deltaTime)
    }

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmInstant?.uiWidget == this && cooldownLeft == 0L) {
            onClick()
            cooldownLeft = cooldown
            color = config.colorBoxTernary
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, color)
}