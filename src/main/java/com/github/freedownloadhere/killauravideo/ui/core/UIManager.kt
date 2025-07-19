package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.core.io.*
import com.github.freedownloadhere.killauravideo.ui.core.layout.ILayout
import com.github.freedownloadhere.killauravideo.ui.core.render.UIRenderManager
import com.github.freedownloadhere.killauravideo.ui.dsl.UIBuilderGlobals
import com.github.freedownloadhere.killauravideo.ui.util.RecursiveIterator
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

class UIManager(
    mouse: IMouse,
    keyboard: IKeyboard,
    private val config: UIStyleConfig,
    private val widgetProvider: () -> UIWidget,
) {
    private lateinit var widget: UIWidget
    private lateinit var renderManager: UIRenderManager
    private val inputData = InputData(mouse, keyboard)

    fun updateUI(deltaTime: Long) {
        widget.updateRecursive(deltaTime)
    }

    fun updateUILayout() {
        with(widget) {
            if(this is ILayout)
                applyLayout()
        }
    }

    fun requestUI() {
        UIBuilderGlobals.uiConfig = config
        widget = widgetProvider()
        renderManager = UIRenderManager(config)
    }

    fun renderUI() {
        renderManager.renderEverything(widget)
    }

    fun updateInput() {
        inputData.update(widget, config)
        RecursiveIterator.basic.dfs(widget) {
            if(this is IInputUpdate)
                inputUpdateCallback(inputData)
        }
    }
}