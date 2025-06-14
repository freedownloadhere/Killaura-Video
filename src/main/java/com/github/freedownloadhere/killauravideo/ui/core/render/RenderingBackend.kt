package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum

object RenderingBackend {
    fun drawRect(
        x: Float, y: Float, z: Float,
        width: Float, height: Float,
        baseColor: UIColorEnum, borderColor: UIColorEnum,
        rounding: Float, bordering: Float,
    ) = JavaNativeRendering.nDrawRect(
        x, y, z,
        width, height,
        baseColor.toColor().rgb, borderColor.toColor().rgb,
        rounding, bordering
    )

    fun drawText(
        string: String,
        x: Float, y: Float, z: Float,
        color: UIColorEnum,
        scale: UIText.Scale,
    ) = JavaNativeRendering.nDrawText(
        string,
        x, y, z,
        color.toColor().rgb,
        scale.idx
    )
}