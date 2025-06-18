package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

fun <T> T.uiBoxDraw(ri: IRenderInfo, baseColor: UIColorEnum) where T: UI {
    RenderingBackend.drawRect(
        x = ri.absX + relX,
        y = ri.absY + relY,
        z = ri.layer * 0.01f,
        width = width,
        height = height,
        baseColor = baseColor,
        borderColor = UIColorEnum.BOX_PRIMARY,
        rounding = ri.config.rounding,
        bordering = ri.config.bordering,
    )
}