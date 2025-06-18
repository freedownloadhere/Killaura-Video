package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import org.apache.logging.log4j.LogManager
import java.io.FileNotFoundException

object RenderingBackend {
    private val savedTextureToID = mutableMapOf<String, Int>()

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

    fun drawLine(
        x1: Float, y1: Float, z1: Float,
        x2: Float, y2: Float, z2: Float,
        color: UIColorEnum,
        width: Float,
    ) = JavaNativeRendering.nAddLineToMesh(
        x1, y1, z1,
        x2, y2, z2,
        color.toPackedARGB(),
        width
    )

    fun drawRect(
        x: Float, y: Float, z: Float,
        width: Float, height: Float,
        baseColor: UIColorEnum, borderColor: UIColorEnum,
        rounding: Float, bordering: Float,
        textureName: String? = null,
    ) = JavaNativeRendering.nAddRectToMesh(
        x, y, z,
        width, height,
        baseColor.toColor().rgb, borderColor.toColor().rgb,
        rounding, bordering,
        savedTextureToID.getOrDefault(textureName, -1)
    )

    fun drawText(
        string: String,
        x: Float, y: Float, z: Float,
        color: UIColorEnum,
        scale: UIText.Scale,
    ) = JavaNativeRendering.nAddTextToMesh(
        string,
        x, y, z,
        color.toColor().rgb,
        scale.idx
    )
}