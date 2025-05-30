package com.github.freedownloadhere.killauravideo.ui.interfaces.parents

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface IUniqueParent: IParent {
    val child: UI
    override val children: Sequence<UI>
        get() = sequenceOf(child)
}