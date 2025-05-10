package com.github.freedownloadhere.killauravideo.modules

abstract class Module(
    val name: String
) {
    protected var toggled = false

    fun isEnabled() = toggled
    fun toggle() { toggled = !toggled }

    abstract fun update()
}