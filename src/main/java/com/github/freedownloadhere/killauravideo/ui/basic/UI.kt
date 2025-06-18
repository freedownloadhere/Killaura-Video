package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.dsl.UIScopeMarker
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

@UIScopeMarker
abstract class UI(config: UIStyleConfig): IDrawable {
    var relX: Float = 0.0f
    var relY: Float = 0.0f
    var width: Float = 0.0f
    var height: Float = 0.0f

    var active = true
        set(value) { changeActiveState(value) }

    open fun update(deltaTime: Long) { }

    fun toggleActive() = changeActiveState(!active)
    fun enable() = changeActiveState(true)
    fun disable() = changeActiveState(false)

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