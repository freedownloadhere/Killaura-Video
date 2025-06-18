package com.github.freedownloadhere.killauravideo.ui.core.hierarchy

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

interface IUniqueParent<T>: IParent where T: UIWidget {
    val child: T
    override val children: Sequence<T>
        get() = sequenceOf(child)
}