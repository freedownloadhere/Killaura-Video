package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.rendering.Renderer
import com.github.freedownloadhere.killauravideo.ui.interfaces.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator

abstract class UI {
    internal var relX = 0.0
    internal var relY = 0.0
    internal var width = 0.0
    internal var height = 0.0

    var toggled = true
        private set

    fun renderRecursive() {
        if(!toggled) return
        Renderer.renderIterator.dfs(this) {
            if(this is IDrawable) draw()
        }
    }

    fun updateRecursive(deltaTime: Long) {
        if(!toggled) return
        RecursiveIterator.basic.dfs(this) {
            update(deltaTime)
        }
    }

    open fun update(deltaTime: Long) { }

    open fun toggle() = changeToggleState(!toggled)
    open fun enable() = changeToggleState(true)
    open fun disable() = changeToggleState(false)
    private fun changeToggleState(state: Boolean) = RecursiveIterator.basic.dfs(this) {
        toggled = state
    }
}