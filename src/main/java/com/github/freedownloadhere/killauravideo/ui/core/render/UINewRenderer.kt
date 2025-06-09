package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

class UINewRenderer(config: UIConfig) {
    data class RenderInfo(
        var absX: Double,
        var absY: Double,
        var layer: Int,
        var config: UIConfig,
    )

    private val renderInfo = RenderInfo(
        absX = 0.0, absY = 0.0, layer = 0, config = config
    )

    fun <T> renderEverything(ui: T) where T: UI, T: IDrawable {
        renderInfo.absX = 0.0
        renderInfo.absY = 0.0
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