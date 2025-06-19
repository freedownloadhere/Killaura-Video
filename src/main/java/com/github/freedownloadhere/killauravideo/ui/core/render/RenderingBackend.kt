package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetText
import java.awt.Color
import java.io.FileNotFoundException

object RenderingBackend {
    private val savedTextureToID = mutableMapOf<String, Int>()

    private var absX: Float = 0.0f
    private var absY: Float = 0.0f
    private var absZ: Float = 0.0f

    fun init(screenWidth: Float, screenHeight: Float) {
        JavaNativeRendering.nInit(screenWidth, screenHeight)
    }

    fun loadTexture(name: String, path: String) {
        val inputStream = RenderingBackend::class.java.getResourceAsStream(path)
            ?: throw FileNotFoundException("Failed to find resource $path")
        val inputBytes = inputStream.readBytes()
        savedTextureToID[name] = JavaNativeRendering.nUploadTexture(inputBytes)
        inputStream.close()
    }

    fun translateBy(ri: IRenderInfo, relX: Float, relY: Float) {
        absX = ri.absX + relX
        absY = ri.absY + relY
        absZ = ri.layer * 0.01f
    }

    fun drawLine(
        x1: Float, y1: Float,
        x2: Float, y2: Float,
        color: Color,
        width: Float,
    ) = JavaNativeRendering.nAddLineToMesh(
        absX + x1, absY + y1, absZ,
        absX + x2, absY + y2, absZ,
        color.rgb,
        width
    )

    fun drawRect(
        x: Float, y: Float,
        width: Float, height: Float,
        baseColor: Color, borderColor: Color,
        rounding: Float, bordering: Float,
        textureName: String? = null,
    ) = JavaNativeRendering.nAddRectToMesh(
        absX + x, absY + y, absZ,
        width, height,
        baseColor.rgb, borderColor.rgb,
        rounding, bordering,
        savedTextureToID.getOrDefault(textureName, -1)
    )

    fun drawText(
        string: String,
        x: Float, y: Float,
        color: Color,
        scale: UIWidgetText.Scale,
    ) = JavaNativeRendering.nAddTextToMesh(
        string,
        absX + x, absY + y, absZ,
        color.rgb,
        scale.idx
    )
}