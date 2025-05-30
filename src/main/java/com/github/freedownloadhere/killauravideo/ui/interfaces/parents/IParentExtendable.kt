package com.github.freedownloadhere.killauravideo.ui.interfaces.parents

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface IParentExtendable: IParent {
    fun addChild(ui: UI)

    operator fun UI.unaryPlus() = addChild(this)
}