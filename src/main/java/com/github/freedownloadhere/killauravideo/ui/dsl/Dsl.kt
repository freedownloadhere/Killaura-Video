package com.github.freedownloadhere.killauravideo.ui.dsl

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.composite.UIButton
import com.github.freedownloadhere.killauravideo.ui.composite.UISlider
import com.github.freedownloadhere.killauravideo.ui.containers.UICenteredBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIHorizontalBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIVerticalBox

fun verticalBox(init: UIVerticalBox.() -> Unit = {}) = newUI(UIVerticalBox(), init)

fun horizontalBox(init: UIHorizontalBox.() -> Unit = {}) = newUI(UIHorizontalBox(), init)

fun centerBox(init: UICenteredBox.() -> Unit = {}) = newUI(UICenteredBox(), init)

fun text(defaultText: String = "Text", init: UIText.() -> Unit = {}) = newUI(UIText(defaultText), init)

fun button(init: UIButton.() -> Unit = {}) = newUI(UIButton(), init)

fun slider(init: UISlider.() -> Unit = {}) = newUI(UISlider(), init)

private fun <T: UI> newUI(ui: T, init: T.() -> Unit): T {
    ui.init()
    return ui
}