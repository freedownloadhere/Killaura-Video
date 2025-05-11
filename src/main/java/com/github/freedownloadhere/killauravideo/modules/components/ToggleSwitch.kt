package com.github.freedownloadhere.killauravideo.modules.components

import com.lukflug.panelstudio.base.IToggleable

class ToggleSwitch(
    private var toggled: Boolean = false
) : IToggleable
{
    override fun isOn(): Boolean = toggled
    override fun toggle() { toggled = !toggled }
}