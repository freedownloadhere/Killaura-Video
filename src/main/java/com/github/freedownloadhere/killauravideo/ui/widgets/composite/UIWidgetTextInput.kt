package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IInputUpdate
import com.github.freedownloadhere.killauravideo.ui.core.io.InputData
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.RenderingBackend
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetText
import org.lwjgl.input.Keyboard
import kotlin.math.max
import kotlin.math.min

class UIWidgetTextInput(
    config: UIStyleConfig
) : UIWidget(config),
    IInputUpdate,
    IUniqueParent<UIWidgetText>,
    ILayoutPost,
    IPadded
{
    override var enablePadding: Boolean = true

    private val textBuffer: StringBuilder = StringBuilder()
    private var cursorIdx: Int = 0
    private var cursorPosX: Float = 0.0f

    override val child: UIWidgetText = UIWidgetText(config).apply {
        source = { this@UIWidgetTextInput.textBuffer.toString() }
    }

    override fun inputUpdateCallback(io: InputData) {
        if(io.lcmSelected?.uiWidget != this || io.charTyped == null || io.keyCode == null)
            return

        val charTyped = io.charTyped!!
        val keyCode = io.keyCode!!

        if(!charTyped.isISOControl()) {
            textBuffer.insert(cursorIdx, charTyped)
            ++cursorIdx
        }

        else when(keyCode) {
            Keyboard.KEY_BACK -> {
                if(textBuffer.isNotEmpty() && cursorIdx > 0) {
                    textBuffer.deleteCharAt(cursorIdx - 1)
                    --cursorIdx
                }
            }
            Keyboard.KEY_RIGHT -> {
                cursorIdx = min(textBuffer.length, cursorIdx + 1)
            }
            Keyboard.KEY_LEFT -> {
                cursorIdx = max(0, cursorIdx - 1)
            }
        }

        cursorPosX = child.relX + if(cursorIdx > 0) JavaNativeRendering.nGetTextWidth(
            textBuffer.substring(0, cursorIdx),
            child.scale.idx
        ) else 0.0f
    }

    override fun renderCallback(ri: IRenderInfo) {
        val pad = if(enablePadding) config.padding else 0.0f
        uiBoxDraw(ri, config.colorBoxSecondary)
        RenderingBackend.drawLine(
            cursorPosX, pad,
            cursorPosX, height - pad,
            config.colorTextPrimary,
            1.0f
        )
    }

    override fun layoutPostCallback() {
        val pad = if(enablePadding) config.padding else 0.0f
        height = child.height + 2.0f * pad
        child.relY = pad

        if(cursorPosX > width - pad) {
            val diff = cursorPosX - width + pad
            cursorPosX -= diff
            child.relX -= diff
        }

        else if(cursorPosX < pad) {
            val diff = pad - cursorPosX
            child.relX += diff
            cursorPosX = pad
        }
    }
}