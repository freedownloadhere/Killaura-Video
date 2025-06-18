package com.github.freedownloadhere.killauravideo.ui.core.io

interface IKeyboardEvent : IInteractable {
    fun keyTypedCallback(typedChar : Char, keyCode : Int)
}