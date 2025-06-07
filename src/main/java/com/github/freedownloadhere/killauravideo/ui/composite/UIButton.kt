package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBasicDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IHoverable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import kotlin.math.max

class UIButton(config: UIConfig) : UI(config), IUniqueParent<UIText>, ILayoutPost, IClickable, IHoverable, IDrawable
{
    private val cooldown = config.buttonClickCooldown
    private var cooldownLeft: Long  = 0L
    override var hidden: Boolean = false
    override val child: UIText = UIText(config)
    var onClick: () -> Unit = { }
    val text: UIText
        get() = child
    var color: UIColorEnum = UIColorEnum.NEUTRAL

    override fun update(deltaTime: Long) {
        cooldownLeft = max(0L, cooldownLeft - deltaTime)
        super.update(deltaTime)
    }

    override fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        if(button == 0 && cooldownLeft == 0L) {
            onClick()
            cooldownLeft = cooldown
            color = UIColorEnum.NEUTRAL_DARK
        }
    }

    override fun hoverStartCallback() {
        if(cooldownLeft == 0L)
            color = UIColorEnum.NEUTRAL_LIGHT
    }

    override fun hoverStopCallback() {
        if(cooldownLeft == 0L)
            color = UIColorEnum.NEUTRAL
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback(renderer: Renderer) = uiBasicDraw(renderer)
}