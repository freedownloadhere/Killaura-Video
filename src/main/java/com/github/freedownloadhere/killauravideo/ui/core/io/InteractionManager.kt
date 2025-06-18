package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IMouseEvent
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.ITypable
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class InteractionManager(
    private val mouseInfo: MouseInfo,
    private val topUI: UI,
    private val config: UIStyleConfig,
) {
    var focused: UI? = null
        private set

    fun handleMouseInput() {
        mouseInfo.update(topUI, config)
        RecursiveIterator.basic.dfs(topUI) {
            if(this is IMouseEvent)
                mouseEventCallback(mouseInfo)
        }
    }

    fun handleKeyTyped(typedChar : Char, keyCode : Int) {
        if(focused != null && focused is ITypable)
            (focused as ITypable).keyTypedCallback(typedChar, keyCode)
    }
}