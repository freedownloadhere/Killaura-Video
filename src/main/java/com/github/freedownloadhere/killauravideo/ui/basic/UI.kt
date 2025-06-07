package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.Renderer
import com.github.freedownloadhere.killauravideo.ui.dsl.UIScopeMarker
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

@UIScopeMarker
abstract class UI(config: UIConfig) {
    internal var relX = 0.0
    internal var relY = 0.0
    internal var width = 0.0
    internal var height = 0.0

    var active = true
        set(value) { changeActiveState(value) }

    open fun update(deltaTime: Long) { }

    fun toggleActive() = changeActiveState(!active)
    fun enable() = changeActiveState(true)
    fun disable() = changeActiveState(false)

    fun renderRecursive(renderer: Renderer) {
        if(!active) return
        Renderer.renderIterator.dfs(this) {
            if(this is IDrawable && !hidden)
                renderCallback(renderer)
        }
    }

    fun updateRecursive(deltaTime: Long) {
        if(!active) return
        RecursiveIterator.basic.dfs(this) {
            update(deltaTime)
        }
    }

    private fun changeActiveState(state: Boolean) = RecursiveIterator.basic.dfs(this) {
        active = state
    }
}