package com.github.freedownloadhere.killauravideo.ui.util

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

class RecursiveIterator(
    private val onBegin: (UIWidget.()->Unit)? = null,
    private val onEnd: (UIWidget.()->Unit)? = null
) {
    companion object {
        val basic = RecursiveIterator()
    }

    fun dfs(uiWidget: UIWidget, forEach: UIWidget.()->Unit) {
        onBegin?.invoke(uiWidget)
        uiWidget.forEach()
        if(uiWidget is IParent)
            for(child in uiWidget.children)
                dfs(child, forEach)
        onEnd?.invoke(uiWidget)
    }
}