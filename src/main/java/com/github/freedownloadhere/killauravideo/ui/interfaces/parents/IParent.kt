package com.github.freedownloadhere.killauravideo.ui.interfaces.parents

import com.github.freedownloadhere.killauravideo.ui.basic.UI

interface IParent {
    val children: Sequence<UI>
}