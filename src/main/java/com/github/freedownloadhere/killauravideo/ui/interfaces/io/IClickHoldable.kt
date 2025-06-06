package com.github.freedownloadhere.killauravideo.ui.interfaces.io

interface IClickHoldable: IInteractable {
    fun onClickHold(button: Int, mouseRelX: Double, mouseRelY: Double)
}