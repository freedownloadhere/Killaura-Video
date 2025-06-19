package com.github.freedownloadhere.killauravideo.ui.dsl

import com.github.freedownloadhere.killauravideo.ui.core.UIStyleConfig
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget
import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidgetText
import com.github.freedownloadhere.killauravideo.ui.widgets.composite.UIWidgetButton
import com.github.freedownloadhere.killauravideo.ui.widgets.composite.UIWidgetCheckbox
import com.github.freedownloadhere.killauravideo.ui.widgets.composite.UIWidgetSlider
import com.github.freedownloadhere.killauravideo.ui.widgets.composite.UIWidgetTextInput
import com.github.freedownloadhere.killauravideo.ui.widgets.containers.UIWidgetCenteredBox
import com.github.freedownloadhere.killauravideo.ui.widgets.containers.UIWidgetHorizontalBox
import com.github.freedownloadhere.killauravideo.ui.widgets.containers.UIWidgetVerticalBox

object UIBuilderGlobals {
    lateinit var uiConfig: UIStyleConfig
}

fun verticalBox(init: UIWidgetVerticalBox.() -> Unit = {}) = newUI(UIWidgetVerticalBox(config = UIBuilderGlobals.uiConfig), init)

fun horizontalBox(init: UIWidgetHorizontalBox.() -> Unit = {}) = newUI(UIWidgetHorizontalBox(config = UIBuilderGlobals.uiConfig), init)

fun centerBox(init: UIWidgetCenteredBox<UIWidget>.() -> Unit = {}) = newUI(UIWidgetCenteredBox(config = UIBuilderGlobals.uiConfig), init)

fun text(defaultText: String = "Text", init: UIWidgetText.() -> Unit = {}) = newUI(UIWidgetText(config = UIBuilderGlobals.uiConfig, defaultText), init)

fun button(init: UIWidgetButton.() -> Unit = {}) = newUI(UIWidgetButton(config = UIBuilderGlobals.uiConfig), init)

fun slider(init: UIWidgetSlider.() -> Unit = {}) = newUI(UIWidgetSlider(config = UIBuilderGlobals.uiConfig), init)

fun checkbox(init: UIWidgetCheckbox.() -> Unit = {}) = newUI(UIWidgetCheckbox(config = UIBuilderGlobals.uiConfig), init)

fun textInput(init: UIWidgetTextInput.() -> Unit = {}) = newUI(UIWidgetTextInput(config = UIBuilderGlobals.uiConfig), init)

private fun <T: UIWidget> newUI(ui: T, init: T.() -> Unit): T {
    ui.init()
    return ui
}