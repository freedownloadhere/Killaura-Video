package com.github.freedownloadhere.killauravideo.ui.implementations

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.UINewRenderer
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum

fun <T> T.uiBoxDraw(info: UINewRenderer.RenderInfo, baseColor: UIColorEnum) where T: UI {
    RenderingBackend.drawRect(
        x = info.absX + relX,
        y = info.absY + relY,
        z = info.layer * 0.01f,
        width = width,
        height = height,
        baseColor = baseColor,
        borderColor = UIColorEnum.BOX_PRIMARY,
        rounding = info.config.rounding,
        bordering = info.config.bordering,
    )
}