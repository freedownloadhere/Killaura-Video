package com.github.freedownloadhere.killauravideo.modules

import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.util.BlockPos

class FakeBlocks: Module("FakeBlocks") {
    private val posList = mutableListOf<BlockPos>()

    fun addFake(x: Int, y: Int, z: Int) {
        posList.add(BlockPos(x, y, z))
    }

    override fun update() {
        for(pos in posList)
            Minecraft.getMinecraft().theWorld.invalidateRegionAndSetBlock(
                pos, Block.getStateById(20)
            )
    }
}