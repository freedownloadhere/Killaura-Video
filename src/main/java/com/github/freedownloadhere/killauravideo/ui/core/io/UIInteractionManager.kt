package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

class UIInteractionManager(
    private val topUIWidget: UIWidget,
    private val config: UIStyleConfig,
) {
    private val mi: MouseInfo = MouseInfo()

    fun handleMouseInput() {
        mi.update(topUIWidget, config)
        RecursiveIterator.basic.dfs(topUIWidget) {
            if(this is IMouseEvent)
                mouseEventCallback(mi)
        }
    }

    fun handleKeyTyped(typedChar : Char, keyCode : Int) {
        if(mi.lcmCurrent != null && mi.lcmCurrent?.uiWidget is IKeyboardEvent)
            (mi.lcmCurrent!!.uiWidget as IKeyboardEvent).keyTypedCallback(typedChar, keyCode)
    }
}