package com.github.freedownloadhere.killauravideo.interfaces

import net.minecraft.client.renderer.Tessellator

interface IRenderable {
    fun render(tess: Tessellator)
}