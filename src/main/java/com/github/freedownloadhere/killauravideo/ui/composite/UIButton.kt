package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import kotlin.math.max

class UIButton(config: UIStyleConfig): UI(config), IUniqueParent<UIText>, ILayoutPost, IMouseEvent, IDrawable
{
    private val cooldown: Long = config.buttonClickCooldown
    private var cooldownLeft: Long  = 0L

    override var hidden: Boolean = false
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

    override fun renderCallback(info: UINewRenderer.RenderInfo) = uiBoxDraw(info, color)
}