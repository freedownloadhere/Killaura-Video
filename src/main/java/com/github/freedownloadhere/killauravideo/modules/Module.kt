package com.github.freedownloadhere.killauravideo.modules

import com.github.freedownloadhere.killauravideo.interfaces.IDestructible
import com.github.freedownloadhere.killauravideo.interfaces.IInitializable

abstract class Module(
    val name: String
) : IInitializable, IDestructible
{
    protected var toggled = false

    fun isEnabled() = toggled
    fun toggle() { toggled = !toggled }

    override fun init() { }
    override fun destroy() { }

    abstract fun update()
}