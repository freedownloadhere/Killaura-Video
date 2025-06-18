package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent
import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

class UIRenderManager(config: UIStyleConfig) {
    data class RenderInfo(
        override var absX: Float,
        override var absY: Float,
        override var layer: Int,
        override var config: UIStyleConfig,
    ) : IRenderInfo

    private val renderInfo = RenderInfo(
        absX = 0.0f, absY = 0.0f, layer = 0, config = config
    )

    init {
        JavaNativeRendering.nUpdateScreenSize(config.screenWidth, config.screenHeight)
    }

    fun <T> renderEverything(ui: T) where T: UIWidget {
        renderInfo.absX = 0.0f
        renderInfo.absY = 0.0f
        renderInfo.layer = 0
        recursiveRender(ui)
    }

    private fun <T> recursiveRender(ui: T) where T: UIWidget {
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