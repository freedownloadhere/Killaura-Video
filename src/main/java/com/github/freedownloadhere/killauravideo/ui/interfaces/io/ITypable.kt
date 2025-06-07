package com.github.freedownloadhere.killauravideo.ui.interfaces.io

interface ITypable : IInteractable {
    fun keyTypedCallback(typedChar : Char, keyCode : Int)
}