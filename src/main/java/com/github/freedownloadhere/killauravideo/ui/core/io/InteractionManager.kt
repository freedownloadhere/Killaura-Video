package com.github.freedownloadhere.killauravideo.ui.core.io

import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

class InteractionManager(
    private val topUI: UI,
    private val config: UIStyleConfig,
) {
    private val mi: MouseInfo = MouseInfo()

    fun handleMouseInput() {
        mi.update(topUI, config)
        RecursiveIterator.basic.dfs(topUI) {
            if(this is IMouseEvent)
                mouseEventCallback(mi)
        }
    }

    fun handleKeyTyped(typedChar : Char, keyCode : Int) {
        if(mi.lcmCurrent != null && mi.lcmCurrent?.ui is IKeyboardEvent)
            (mi.lcmCurrent!!.ui as IKeyboardEvent).keyTypedCallback(typedChar, keyCode)
    }
}