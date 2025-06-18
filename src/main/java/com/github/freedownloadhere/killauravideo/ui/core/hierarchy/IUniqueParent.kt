package com.github.freedownloadhere.killauravideo.ui.core.hierarchy

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

interface IUniqueParent<T>: IParent where T: UI {
    val child: T
    override val children: Sequence<T>
        get() = sequenceOf(child)
}