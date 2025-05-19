package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.interfaces.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.ITypable
import com.github.freedownloadhere.killauravideo.ui.utils.UILayoutUtils
import com.github.freedownloadhere.killauravideo.utils.ColorHelper
import org.lwjgl.input.Keyboard
import kotlin.math.max
import kotlin.math.min

class UITextBox(private val placeholder : String)
    : UI(), ITypable, IDrawable, ILayoutPost, IParent
{
    override var baseColor = ColorHelper.GuiNeutralDark
    override val children = listOf(UIText(placeholder))
    private val textGui : UIText
        get() = children[0]
    private val builder = StringBuilder()
    private var cursorPos = 0

    init { textGui.baseColor = ColorHelper.GuiNeutralLight }

    override fun onKeyTyped(typedChar: Char, keyCode: Int) {
        try {
            if(specialKeyMap.containsKey(keyCode))
                specialKeyMap[keyCode]!!.invoke(this)
            else if(!typedChar.isISOControl()) {
                builder.insert(cursorPos, typedChar)
                cursorPos++
            }

            textGui.str = if(builder.isEmpty()) {
                textGui.baseColor = ColorHelper.GuiNeutralLight
                placeholder
            } else {
                textGui.baseColor = ColorHelper.White
                builder.toString()
            }
        } catch(e : IndexOutOfBoundsException) {
            println(e.message)
        }
    }

    private companion object {
        val specialKeyMap : Map<Int, (UITextBox) -> Unit> = mapOf(
            Keyboard.KEY_BACK to {
                instance : UITextBox ->

                if(instance.builder.isNotEmpty() && instance.cursorPos > 0) {
                    instance.builder.deleteAt(instance.cursorPos - 1)
                    instance.cursorPos--
                    instance.cursorPos = max(instance.cursorPos, 0)
                }
            },
            Keyboard.KEY_RIGHT to {
                instance : UITextBox ->

                instance.cursorPos++
                instance.cursorPos = min(instance.cursorPos, instance.builder.length)
            },
            Keyboard.KEY_LEFT to {
                instance : UITextBox ->

                instance.cursorPos--
                instance.cursorPos = max(instance.cursorPos, 0)
            }
        )
    }

    override fun applyLayoutPost() {
        UILayoutUtils.stretchToFitHeight(this)
        UILayoutUtils.list(this, 0.0, 0.0)
    }

    override fun draw() {
        UICore.renderer.drawBasicBG(this)
    }

    override fun update(deltaTime: Long) {
        UICore.renderer.scissorStack.push(this)
        super.update(deltaTime)
        UICore.renderer.scissorStack.pop()
    }
}