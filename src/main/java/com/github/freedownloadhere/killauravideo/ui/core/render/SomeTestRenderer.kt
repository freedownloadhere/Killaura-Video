package com.github.freedownloadhere.killauravideo.ui.core.render

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

private const val MOD_ID = "killauravideo"

object SomeTestRenderer {
    private val vertexArray = floatArrayOf(
        -0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f,
         0.5f, -0.5f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f,
         0.0f,  0.5f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f,
    )

    private val vertexFloatBuffer = BufferUtils.createFloatBuffer(21).apply {
        put(vertexArray)
        flip()
    }

    private var program: Int = 0
    private var vao: Int = 0
    private var vbo: Int = 0

    init {
        val vertShader = readAndCreateShader("triangle.vert", GL_VERTEX_SHADER)
        val fragShader = readAndCreateShader("triangle.frag", GL_FRAGMENT_SHADER)

        program = glCreateProgram()
        glAttachShader(program, vertShader)
        glAttachShader(program, fragShader)
        glLinkProgram(program)
        glDeleteShader(vertShader)
        glDeleteShader(fragShader)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertexFloatBuffer, GL_STATIC_DRAW)

        vao = glGenVertexArrays()
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * 4, 0L)
        glEnableVertexAttribArray(1)
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * 4, 3L * 4L)

        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun renderTriangle() {
        glUseProgram(program)
        glBindVertexArray(vao)

        glDrawArrays(GL_TRIANGLES, 0, 3)

        glBindVertexArray(0)
        glUseProgram(0)
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