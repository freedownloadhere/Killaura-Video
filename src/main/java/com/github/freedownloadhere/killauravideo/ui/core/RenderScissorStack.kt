package com.github.freedownloadhere.killauravideo.ui.core

import com.github.freedownloadhere.killauravideo.ui.UI
import org.lwjgl.opengl.GL11
import java.util.Stack
import kotlin.math.max
import kotlin.math.min

class RenderScissorStack {
    data class ScissorData(
        var x1 : Int,
        var y1 : Int,
        var x2 : Int,
        var y2 : Int
    )

    private val stk = Stack<ScissorData>()

    fun push(gui : UI) {
        val a = ScissorData(gui.relX.toInt(), gui.relY.toInt(), (gui.relX + gui.width).toInt(), (gui.relY + gui.height).toInt())

        if(stk.empty()) {
            stk.push(a)
            apply()
            return
        }

        val b = stk.peek()

        if(a.x2 <= b.x1 || a.x1 >= b.x2 || a.y2 <= b.y1 || a.y1 >= b.y2) {
            stk.push(ScissorData(0, 0, 0, 0))
            return
        }

        stk.push(
            ScissorData(
                max(a.x1, b.x1),
                max(a.y1, b.y1),
                min(a.x2, b.x2),
                min(a.y2, b.y2)
            )
        )

        apply()
    }

    fun pop() {
        stk.pop()
        if(!stk.empty())
            apply()
    }

    fun disable() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
    }

    fun enable() {
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        GL11.glScissor(0, 0, Core.width, Core.height)
    }

    /**
     * everything should be applied by the stack push / pop
     */
    private fun apply() {
        val thickness = Core.config.borderThickness.toInt()
        val top = stk.peek()
        GL11.glScissor(
            top.x1 - thickness,
            Core.height - thickness - top.y2,
            (top.x2 - top.x1) + 2 * thickness,
            (top.y2 - top.y1) + 2 * thickness
        )
    }
}