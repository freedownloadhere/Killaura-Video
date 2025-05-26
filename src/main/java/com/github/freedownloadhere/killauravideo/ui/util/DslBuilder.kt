package com.github.freedownloadhere.killauravideo.ui.util

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.ui.containers.UIFreeBox

fun ui(init: UIFreeBox.()->Unit): UIFreeBox {
    val box = UIFreeBox()
    box.padded = false
    box.hidden = true
    box.width = GlobalManager.core!!.config.screenWidth
    box.height = GlobalManager.core!!.config.screenHeight
    box.init()
    return box
}