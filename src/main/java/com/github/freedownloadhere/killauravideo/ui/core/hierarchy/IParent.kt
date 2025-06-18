package com.github.freedownloadhere.killauravideo.ui.core.hierarchy

import com.github.freedownloadhere.killauravideo.ui.widgets.basic.UI

interface IParent {
    val children: Sequence<UI>
}