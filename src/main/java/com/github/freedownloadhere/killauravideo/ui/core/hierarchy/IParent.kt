package com.github.freedownloadhere.killauravideo.ui.core.hierarchy

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UIWidget

interface IParent {
    val children: Sequence<UIWidget>
}