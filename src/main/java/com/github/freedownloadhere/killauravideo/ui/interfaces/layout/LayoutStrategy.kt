package com.github.freedownloadhere.killauravideo.ui.interfaces.layout

import com.github.freedownloadhere.killauravideo.ui.basic.UI

abstract class LayoutStrategy<T: UI> {
    abstract fun applyFor(ui: T)
}