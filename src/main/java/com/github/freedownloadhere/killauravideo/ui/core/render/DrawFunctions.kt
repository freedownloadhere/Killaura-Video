package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import java.awt.Color

fun <T> T.uiBoxDraw(ri: IRenderInfo, baseColor: Color) where T: UIWidget {
    RenderingBackend.translateBy(ri, relX, relY)
    RenderingBackend.drawRect(
        x = 0.0f,
        y = 0.0f,
        width = width,
        height = height,
        baseColor = baseColor,
        borderColor = ri.config.colorBoxPrimary,
        rounding = ri.config.rounding,
        bordering = ri.config.bordering,
    )
}