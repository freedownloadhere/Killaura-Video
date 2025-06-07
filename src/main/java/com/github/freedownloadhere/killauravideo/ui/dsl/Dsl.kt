package com.github.freedownloadhere.killauravideo.ui.dsl

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.composite.UIButton
import com.github.freedownloadhere.killauravideo.ui.composite.UICheckbox
import com.github.freedownloadhere.killauravideo.ui.composite.UISlider
import com.github.freedownloadhere.killauravideo.ui.containers.UICenteredBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIHorizontalBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIVerticalBox
import com.github.freedownloadhere.killauravideo.ui.util.UIConfig

object UIBuilderGlobals {
    lateinit var uiConfig: UIConfig
}

fun verticalBox(init: UIVerticalBox.() -> Unit = {}) = newUI(UIVerticalBox(config = UIBuilderGlobals.uiConfig), init)

fun horizontalBox(init: UIHorizontalBox.() -> Unit = {}) = newUI(UIHorizontalBox(config = UIBuilderGlobals.uiConfig), init)

fun centerBox(init: UICenteredBox<UI>.() -> Unit = {}) = newUI(UICenteredBox(config = UIBuilderGlobals.uiConfig), init)

fun text(defaultText: String = "Text", init: UIText.() -> Unit = {}) = newUI(UIText(config = UIBuilderGlobals.uiConfig, defaultText), init)

fun button(init: UIButton.() -> Unit = {}) = newUI(UIButton(config = UIBuilderGlobals.uiConfig), init)

fun slider(init: UISlider.() -> Unit = {}) = newUI(UISlider(config = UIBuilderGlobals.uiConfig), init)

fun checkbox(init: UICheckbox.() -> Unit = {}) = newUI(UICheckbox(config = UIBuilderGlobals.uiConfig), init)

private fun <T: UI> newUI(ui: T, init: T.() -> Unit): T {
    ui.init()
    return ui
}