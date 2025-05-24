package com.github.freedownloadhere.killauravideo.ui.util

import com.github.freedownloadhere.killauravideo.ui.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.IParent

class RecursiveIterator(
    private val onBegin: (UI.()->Unit)? = null,
    private val onEnd: (UI.()->Unit)? = null
) {
    companion object {
        val basic = RecursiveIterator()
    }

    fun dfs(ui: UI, forEach: UI.()->Unit) {
        onBegin?.invoke(ui)
        ui.forEach()
        if(ui is IParent)
            for(child in ui.children)
                dfs(ui, forEach)
        onEnd?.invoke(ui)
    }
}