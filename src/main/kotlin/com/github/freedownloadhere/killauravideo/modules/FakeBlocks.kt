package com.github.freedownloadhere.killauravideo.modules

import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.util.BlockPos

class FakeBlocks: Module("FakeBlocks")
{
    private val posList = mutableListOf<BlockPos>()

    private val world = Minecraft.getMinecraft().theWorld

    fun addFake(x: Int, y: Int, z: Int) {
        posList.add(BlockPos(x, y, z))
    }

    override fun update() {
        for(pos in posList)
            world.invalidateRegionAndSetBlock(
                pos, Block.getStateById(20)
            )
    }
}