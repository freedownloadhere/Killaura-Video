package com.github.freedownloadhere.killauravideo.ui.interfaces.io

interface ITypable : IInteractable {
    fun onKeyTyped(typedChar : Char, keyCode : Int)
}