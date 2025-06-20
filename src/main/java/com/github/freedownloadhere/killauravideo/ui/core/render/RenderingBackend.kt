package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetText
import org.apache.logging.log4j.LogManager
import java.awt.Color
import java.io.FileNotFoundException
import java.util.*
import kotlin.math.max
import kotlin.math.min

object RenderingBackend {
    private data class ScissorRectangle(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
    )

    private val scissorStack = Stack<ScissorRectangle>()

    private val savedTextureToID = mutableMapOf<String, Int>()

    private var absX: Float = 0.0f
    private var absY: Float = 0.0f
    private var absZ: Float = 0.0f

    fun init(screenWidth: Float, screenHeight: Float) {
        JavaNativeRendering.nInit(screenWidth, screenHeight)
        updateScreenSize(screenWidth, screenHeight)
    }

    fun updateScreenSize(screenWidth: Float, screenHeight: Float) {
        JavaNativeRendering.nUpdateScreenSize(screenWidth, screenHeight)
        scissorStack.clear()
        val sr = ScissorRectangle(0.0f, 0.0f, screenWidth, screenHeight)
        scissorStack.push(sr)
        JavaNativeRendering.nSetScissor(
            sr.x1, sr.y1, sr.x2, sr.y2
        )
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

    fun pushScissor(x1: Float, y1: Float, x2: Float, y2: Float) {
        val top = scissorStack.peek()

        val sr = ScissorRectangle(
            max(x1, top.x1),
            max(y1, top.y1),
            min(x2, top.x2),
            min(y2, top.y2),
        )

        scissorStack.push(sr)

        JavaNativeRendering.nSetScissor(
            sr.x1, sr.y1, sr.x2, sr.y2
        )
    }

    fun popScissor() {
        if(scissorStack.size <= 1) {
            LogManager.getLogger().error("Scissor stack underflow")
            return
        }

        scissorStack.pop()
        val top = scissorStack.peek()

        JavaNativeRendering.nSetScissor(
            top.x1, top.y1, top.x2, top.y2
        )
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