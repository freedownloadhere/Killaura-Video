package com.github.freedownloadhere.killauravideo.ui.widgets.composite

import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IUniqueParent
import com.github.freedownloadhere.killauravideo.ui.core.io.IKeyboardEvent
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.core.layout.IPadded
import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.core.render.JavaNativeRendering
import com.github.freedownloadhere.killauravideo.ui.core.render.uiBoxDraw
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetText
import org.lwjgl.input.Keyboard
import kotlin.math.max
import kotlin.math.min

class UIWidgetTextInput(
    config: UIStyleConfig
) : UIWidget(config),
    IKeyboardEvent,
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

    override fun keyTypedCallback(typedChar: Char, keyCode: Int) {
        if(!typedChar.isISOControl()) {
            textBuffer.insert(cursorIdx, typedChar)
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
        uiBoxDraw(ri, config.colorBoxSecondary)
    }

    override fun layoutPostCallback() {
        height = child.height + if(enablePadding) config.padding else 0.0f

        if(cursorPosX > width) {
            val diff = cursorPosX - width
            cursorPosX -= diff
            child.relX -= diff
        }

        else if(cursorPosX < 0.0f) {
            child.relX -= cursorPosX
            cursorPosX = 0.0f
        }
    }
}