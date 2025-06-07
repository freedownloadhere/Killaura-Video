package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.implementations.uiBasicDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.uiCenterBoxLayout
import com.github.freedownloadhere.killauravideo.utils.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IHoverable
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IUniqueParent
import kotlin.math.max

class UIButton
    : UI(),
    IUniqueParent,
    ILayoutPost,
    IClickable,
    IHoverable,
    IDrawable
{
    private var clickCooldown: Long  = 0L
    override var baseColor: UIColorEnum = UIColorEnum.NEUTRAL

    override var hidden: Boolean = false
    override val child: UI = UIText()

    var onClick: () -> Unit = { }

    val text: UIText
        get() = child as UIText

    override fun update(deltaTime: Long) {
        clickCooldown = max(0L, clickCooldown - deltaTime)
        super.update(deltaTime)
    }

    override fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        if(button == 0 && clickCooldown == 0L) {
            onClick()
            clickCooldown = GlobalManager.core!!.config.buttonClickCooldown
            baseColor = UIColorEnum.NEUTRAL_DARK
        }
    }

    override fun hoverStartCallback() {
        if(clickCooldown == 0L)
            baseColor = UIColorEnum.NEUTRAL_LIGHT
    }

    override fun hoverStopCallback() {
        if(clickCooldown == 0L)
            baseColor = UIColorEnum.NEUTRAL
    }

    override fun layoutPostCallback() = uiCenterBoxLayout()

    override fun renderCallback() = uiBasicDraw()
}