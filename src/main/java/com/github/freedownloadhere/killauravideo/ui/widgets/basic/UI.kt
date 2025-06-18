package com.github.freedownloadhere.killauravideo.ui.widgets.basic

import com.github.freedownloadhere.killauravideo.ui.core.render.IRenderInfo
import com.github.freedownloadhere.killauravideo.ui.dsl.UIScopeMarker
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

@Suppress("UNUSED_PARAMETER")
@UIScopeMarker
abstract class UI(config: UIStyleConfig) {
    var relX: Float = 0.0f
    var relY: Float = 0.0f
    var width: Float = 0.0f
    var height: Float = 0.0f

    var active = true
        set(value) { changeActiveState(value) }
    var hidden: Boolean = false

    open fun update(deltaTime: Long) { }

    open fun renderCallback(ri: IRenderInfo) { }

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