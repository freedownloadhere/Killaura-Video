package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import java.awt.Color

fun <T> T.uiBoxDraw(ri: IRenderInfo, baseColor: Color) where T: UIWidget {
    RenderingBackend.drawRect(
        x = ri.absX + relX,
        y = ri.absY + relY,
        z = ri.layer * 0.01f,
        width = width,
        height = height,
        baseColor = baseColor,
        borderColor = ri.config.colorBoxPrimary,
        rounding = ri.config.rounding,
        bordering = ri.config.bordering,
    )
}