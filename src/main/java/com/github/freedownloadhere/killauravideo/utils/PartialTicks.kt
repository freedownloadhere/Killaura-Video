package com.github.freedownloadhere.killauravideo.utils

import com.github.freedownloadhere.killauravideo.mixin.AccessorMinecraft
import net.minecraft.client.Minecraft

object PartialTicks {
    val asFloat: Float
        get() = ((Minecraft.getMinecraft()) as AccessorMinecraft).timer_killauravideo.renderPartialTicks
    val asDouble: Double
        get() = asFloat.toDouble()
}