package com.github.freedownloadhere.killauravideo.modules

import com.github.freedownloadhere.killauravideo.modules.components.ToggleSwitch
import com.lukflug.panelstudio.base.IToggleable
import com.lukflug.panelstudio.setting.IModule
import com.lukflug.panelstudio.setting.ISetting
import java.util.stream.Stream

abstract class Module(
    val name: String
) : IModule
{
    val toggleSwitch = ToggleSwitch()
    private val settings = listOf<ISetting<*>>()

    open fun init() {}
    open fun destroy() {}
    abstract fun update()

    override fun getDisplayName(): String = name
    override fun isEnabled(): IToggleable = toggleSwitch
    override fun getSettings(): Stream<ISetting<*>> = settings.stream()
}