package com.github.freedownloadhere.killauravideo.ui.interfaces.io

interface IClickHoldable: IInteractable {
    fun clickHoldCallback(button: Int, mouseRelX: Double, mouseRelY: Double)
}