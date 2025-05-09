package com.github.freedownloadhere.killauravideo

import net.minecraft.client.renderer.Tessellator

interface IRenderable {
    fun render(tess: Tessellator)
}