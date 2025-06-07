package com.github.freedownloadhere.killauravideo.ui.composite

import com.github.freedownloadhere.killauravideo.ui.basic.UIIcon
import com.github.freedownloadhere.killauravideo.ui.containers.UICenteredBox
import com.github.freedownloadhere.killauravideo.ui.interfaces.io.IClickable

class UICheckbox: UICenteredBox<UIIcon>(), IClickable {
    var checked: Boolean = false
    var onCheck: () -> Unit = { }
    override var child: UIIcon = UIIcon()

    override fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double) {
        checked = !checked
        child.hidden = !checked
        onCheck()
    }
}