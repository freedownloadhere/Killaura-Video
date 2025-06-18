package com.github.freedownloadhere.killauravideo.ui.core.hierarchy

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

interface IParentExtendable: IParent {
    fun addChild(ui: UI)

    operator fun UI.unaryPlus() = addChild(this)
}