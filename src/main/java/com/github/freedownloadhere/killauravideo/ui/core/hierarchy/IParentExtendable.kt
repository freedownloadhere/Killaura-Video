package com.github.freedownloadhere.killauravideo.ui.core.hierarchy

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

interface IParentExtendable: IParent {
    fun addChild(uiWidget: UIWidget)

    operator fun UIWidget.unaryPlus() = addChild(this)
}