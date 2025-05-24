package com.github.freedownloadhere.killauravideo.ui.demo

import com.github.freedownloadhere.killauravideo.ui.*
import java.util.*

class WindowBuilder(title : String) {
    private val window = UIWindow(title)
    private val editStack = Stack<UIListContainer>()

    init {
        editStack.push(window.contents)
    }

    fun beginList() : WindowBuilder {
        val top = editStack.peek()
        val gui = UIListContainer()
        top.addChild(gui)
        editStack.push(gui)
        return this
    }

    fun endList() : WindowBuilder {
        val top = editStack.pop()
        top.applyLayoutPost()
        return this
    }

    fun header(str : String) : WindowBuilder {
        val top = editStack.peek()
        val gui = UIHeader(str, top)
        top.addChild(gui)
        return this
    }

    fun paragraph(str : String) : WindowBuilder {
        val top = editStack.peek()
        val gui = UIParagraph(str, top)
        top.addChild(gui)
        return this
    }

    fun button(str : String, callback : () -> Unit) : WindowBuilder {
        val top = editStack.peek()
        val gui = UITextButton(str, callback)
        top.addChild(gui)
        return this
    }

    fun textBox(placeholder : String) : WindowBuilder {
        val top = editStack.peek()
        val gui = UITextBox(placeholder)
        top.addChild(gui)
        return this
    }

    fun finish() : UIWindow {
        assert(editStack.size == 1)
        editStack.pop()
        assert(editStack.empty())
        window.applyLayout()
        return window
    }
}