package com.github.freedownloadhere.killauravideo.ui.interfaces.parents

import com.github.freedownloadhere.killauravideo.ui.basic.UI
import com.github.freedownloadhere.killauravideo.ui.composite.UIButton
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.containers.UICenteredBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIFreeBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIHorizontalBox
import com.github.freedownloadhere.killauravideo.ui.containers.UIVerticalBox

interface IParentExtendable: IParent {
    fun vbox(init: UIVerticalBox.()->Unit = {}) = child(UIVerticalBox(), init)

    fun hbox(init: UIHorizontalBox.()->Unit = {}) = child(UIHorizontalBox(), init)

    fun <T: UI> centerbox(ui: T, init: UICenteredBox<T>.()->Unit = {}) = child(UICenteredBox(ui), init)

    fun freebox(init: UIFreeBox.()->Unit = {}) = child(UIFreeBox(), init)

    fun text(s: String, init: UIText.()->Unit = {}) = child(UIText(s), init)
    
    fun button(init: UIButton.()->Unit = {}) = child(UIButton(), init)

    fun <T: UI> child(ui: T, init: T.()->Unit = {}): T
}