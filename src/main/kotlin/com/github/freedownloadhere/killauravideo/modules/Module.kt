package com.github.freedownloadhere.killauravideo.modules

import com.github.freedownloadhere.killauravideo.interfaces.IDestructible
import com.github.freedownloadhere.killauravideo.interfaces.IInitializable
import com.github.freedownloadhere.killauravideo.interfaces.INamed

abstract class Module(override val name: String): IInitializable, IDestructible, INamed
{
    var toggled = false
        protected set

    fun isEnabled() = toggled
    fun toggle() { toggled = !toggled }

    override fun init() { }
    override fun destroy() { }

    abstract fun update()
}