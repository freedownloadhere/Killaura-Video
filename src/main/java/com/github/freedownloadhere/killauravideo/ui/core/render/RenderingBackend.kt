package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.joml.Matrix4fStack
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import org.lwjgl.stb.STBTTBakedChar
import org.lwjgl.stb.STBTTFontinfo
import org.lwjgl.stb.STBTruetype.*
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

object RenderingBackend {
    fun drawRect(
        x: Float, y: Float, z: Float,
        width: Float, height: Float,
        baseColor: UIColorEnum, borderColor: UIColorEnum,
        rounding: Float, bordering: Float,
    ) = JavaNativeRendering.nDrawRect(
        x, y, z,
        width, height,
        baseColor.toColor(), borderColor.toColor(),
        rounding, bordering
    )

    fun drawText(
        string: String,
        x: Float, y: Float, z: Float,
        color: UIColorEnum,
        scale: UIText.Scale,
    ) = JavaNativeRendering.nDrawText(
        string,
        x, y, z,
        color.toColor(),
        scale.numeric.toFloat()
    )
}