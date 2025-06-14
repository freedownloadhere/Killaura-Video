package com.github.freedownloadhere.killauravideo.ui.interfaces.io

import com.github.freedownloadhere.killauravideo.ui.core.io.MouseInfo

interface IMouseEvent: IInteractable {
    fun mouseEventCallback(mouseInfo: MouseInfo)
}