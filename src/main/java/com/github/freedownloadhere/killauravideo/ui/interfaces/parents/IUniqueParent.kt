package com.github.freedownloadhere.killauravideo.ui.interfaces.parents

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface IUniqueParent<T>: IParent where T: UI {
    val child: T
    override val children: Sequence<T>
        get() = sequenceOf(child)
}