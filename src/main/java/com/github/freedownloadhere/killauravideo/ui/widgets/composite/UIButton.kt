package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIText
import kotlin.math.max

class UIButton(config: UIStyleConfig): UI(config), IUniqueParent<UIText>, ILayoutPost, IMouseEvent
{
    private val cooldown: Long = config.buttonClickCooldown
    private var cooldownLeft: Long  = 0L

    override val child: UIText = UIText(config)

    var onClick: () -> Unit = { }
    private var color: UIColorEnum = UIColorEnum.BOX_SECONDARY

    override fun update(deltaTime: Long) {
        cooldownLeft = max(0L, cooldownLeft - deltaTime)
        if(cooldownLeft == 0L) color = UIColorEnum.BOX_SECONDARY
        super.update(deltaTime)
    }

    override fun mouseEventCallback(mouseInfo: MouseInfo) {
        if(mouseInfo.lcmInstant?.ui == this && cooldownLeft == 0L) {
            onClick()
            cooldownLeft = cooldown
            color = UIColorEnum.BOX_TERNARY
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(ri: IRenderInfo) = uiBoxDraw(ri, color)
}