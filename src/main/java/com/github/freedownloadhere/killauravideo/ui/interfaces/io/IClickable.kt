package com.github.freedownloadhere.killauravideo.ui.interfaces.io

interface IClickable : IInteractable {
    fun onClick(button: Int, mouseRelX: Double, mouseRelY: Double)
}