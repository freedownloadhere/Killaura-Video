package com.github.freedownloadhere.killauravideo.ui.interfaces

import com.github.freedownloadhere.killauravideo.ui.interfaces.IInteractable

interface IScrollable : IInteractable {
    fun onScroll(d : Int)
}