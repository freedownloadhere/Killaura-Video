package com.github.freedownloadhere.killauravideo.ui.interfaces

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface IUniqueParent<T: UI>: IParent {
    val child: T

    override val children: Sequence<T>
        get() = sequenceOf(child)
}