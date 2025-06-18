package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIStyleConfig

class UINewRenderer(config: UIStyleConfig) {
    data class RenderInfo(
        var absX: Float,
        var absY: Float,
        var layer: Int,
        var config: UIStyleConfig,
    )

    private val renderInfo = RenderInfo(
        absX = 0.0f, absY = 0.0f, layer = 0, config = config
    )

    fun <T> renderEverything(ui: T) where T: UI, T: IDrawable {
        renderInfo.absX = 0.0f
        renderInfo.absY = 0.0f
        renderInfo.layer = 0
        recursiveRender(ui)
    }

    private fun <T> recursiveRender(ui: T) where T: UI, T: IDrawable {
        if(!ui.hidden) ui.renderCallback(renderInfo)
        renderInfo.absX += ui.relX
        renderInfo.absY += ui.relY
        ++renderInfo.layer
        if(ui is IParent) for(child in ui.children) {
            recursiveRender(child)
        }
        --renderInfo.layer
        renderInfo.absY -= ui.relY
        renderInfo.absX -= ui.relX
    }
}