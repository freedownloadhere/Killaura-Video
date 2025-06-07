package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import kotlin.math.max

class UIButton(config: UIConfig): UI(config), IUniqueParent<UIText>, ILayoutPost, IClickable, IDrawable
{
    private val cooldown = config.buttonClickCooldown
    private var cooldownLeft: Long  = 0L
    override var hidden: Boolean = false
    override val child: UIText = UIText(config)
    var onClick: () -> Unit = { }
    val text: UIText
        get() = child
    private var color: UIColorEnum = UIColorEnum.BOX_SECONDARY

    override fun update(deltaTime: Long) {
        cooldownLeft = max(0L, cooldownLeft - deltaTime)
        if(cooldownLeft == 0L) color = UIColorEnum.BOX_SECONDARY
        super.update(deltaTime)
    }

    override fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        if(button == 0 && cooldownLeft == 0L) {
            onClick()
            cooldownLeft = cooldown
            color = UIColorEnum.BOX_TERNARY
        }
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(renderer: Renderer) = uiBoxDraw(renderer, color)
}