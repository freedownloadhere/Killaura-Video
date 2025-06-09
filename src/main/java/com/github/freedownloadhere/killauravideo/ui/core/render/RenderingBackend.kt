package com.github.freedownloadhere.killauravideo.ui.core.render

import com.github.freedownloadhere.killauravideo.mixin.AccessorFontRenderer
import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.util.UIColorEnum
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.joml.Matrix4fStack
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.nio.FloatBuffer
import java.nio.IntBuffer

private const val MOD_ID = "killauravideo"

object RenderingBackend {
    private val rectVertices = BufferUtils.createFloatBuffer(12).put(floatArrayOf(
        0.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        1.0f, 1.0f, 0.0f,
        1.0f, 0.0f, 0.0f,
    )).flip() as FloatBuffer

    private val rectIndices = BufferUtils.createIntBuffer(6).put(intArrayOf(
        0, 1, 2, 2, 3, 0
    )).flip() as IntBuffer

    private var program: Int = 0
    private var vao: Int = 0
    private var vbo: Int = 0
    private var ebo: Int = 0

    init {
        val vertShader = readAndCreateShader("uiRect.vert", GL_VERTEX_SHADER)
        val fragShader = readAndCreateShader("uiRect.frag", GL_FRAGMENT_SHADER)

        program = glCreateProgram()
        glAttachShader(program, vertShader)
        glAttachShader(program, fragShader)
        glLinkProgram(program)
        glDeleteShader(vertShader)
        glDeleteShader(fragShader)
        if(glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            LogManager.getLogger().error("Failed to link program: ${glGetProgramInfoLog(program, 1024)}")
        }

        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)

        ebo = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)

        glEnableVertexAttribArray(0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L)

        glBindVertexArray(0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun drawRect(
        x: Float, y: Float, z: Float,
        width: Float, height: Float,
        screenWidth: Float, screenHeight: Float,
        baseColor: UIColorEnum, borderColor: UIColorEnum,
        rounding: Float, bordering: Float,
    ) {
        glBindVertexArray(vao)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, rectVertices, GL_STATIC_DRAW)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, rectIndices, GL_STATIC_DRAW)

        glUseProgram(program)

        val fb = BufferUtils.createFloatBuffer(16)

        val modelStack = Matrix4fStack(1)
        modelStack.translate(x, y, z)
        modelStack.scale(width, height, 1.0f)
        modelStack.get(fb)
        val modelUni = glGetUniformLocation(program, "uModel")
        glUniformMatrix4(modelUni, false, fb)

        val perspectiveStack = Matrix4fStack(1)
        perspectiveStack.ortho(0.0f, screenWidth, screenHeight, 0.0f, -1.0f, 1.0f)
        perspectiveStack.get(fb)
        val projUni = glGetUniformLocation(program, "uProj")
        glUniformMatrix4(projUni, false, fb)

        val topLeftUni = glGetUniformLocation(program, "uTopLeft")
        glUniform2f(topLeftUni, x, y)
        val bottomRightUni = glGetUniformLocation(program, "uBottomRight")
        glUniform2f(bottomRightUni, x + width, y + height)

        val baseColorUni = glGetUniformLocation(program, "uBaseColor")
        glUniform4f(baseColorUni, baseColor.rFloat, baseColor.gFloat, baseColor.bFloat, baseColor.aFloat)
        val borderColorUni = glGetUniformLocation(program, "uBorderColor")
        glUniform4f(borderColorUni, borderColor.rFloat, borderColor.gFloat, borderColor.bFloat, borderColor.aFloat)

        val roundingUni = glGetUniformLocation(program, "uRounding")
        glUniform1f(roundingUni, rounding)
        val borderingUni = glGetUniformLocation(program, "uBordering")
        glUniform1f(borderingUni, bordering)

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0L)

        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
        glUseProgram(0)
    }

    // TODO un-immediate this
    fun drawText(
        string: String,
        color: UIColorEnum,
        scale: UIText.Scale,
    ) {
        val fr = Minecraft.getMinecraft().fontRendererObj
        val fontTex = (fr as AccessorFontRenderer).fontLocation_killauravideo
        GlStateManager.enableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        Minecraft.getMinecraft().textureManager.bindTexture(fontTex)
        GlStateManager.pushMatrix()
        GlStateManager.scale(scale.numeric, scale.numeric, 1.0)
        fr.drawStringWithShadow(string, 0.0f, 0.0f, color.toPackedARGB())
        GlStateManager.popMatrix()
        GlStateManager.disableBlend()
        GlStateManager.disableTexture2D()
    }

    private fun readAndCreateShader(filename: String, vertOrFrag: Int): Int {
        val resLocation = ResourceLocation(MOD_ID, "shaders/$filename")
        val inputStream = Minecraft.getMinecraft().resourceManager.getResource(resLocation).inputStream
        val shaderCode = inputStream.readBytes().decodeToString()
        inputStream.close()
        val shader = glCreateShader(vertOrFrag)
        glShaderSource(shader, shaderCode)
        glCompileShader(shader)
        if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            val infoLog = glGetShaderInfoLog(shader, 1024)
            LogManager.getLogger().error("Failed to build shader $filename: $infoLog")
            return 0
        }
        return shader
    }
}