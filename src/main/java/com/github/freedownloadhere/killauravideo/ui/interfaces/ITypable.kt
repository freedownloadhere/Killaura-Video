package com.github.freedownloadhere.killauravideo.ui.interfaces

import com.github.freedownloadhere.killauravideo.ui.interfaces.IInteractable

interface ITypable : IInteractable {
    fun onKeyTyped(typedChar : Char, keyCode : Int)
}