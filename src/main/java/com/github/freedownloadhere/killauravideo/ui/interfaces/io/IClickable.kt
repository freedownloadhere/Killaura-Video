package com.github.freedownloadhere.killauravideo.ui.interfaces.io

interface IClickable : IInteractable {
    fun clickCallback(button: Int, mouseRelX: Double, mouseRelY: Double)
}