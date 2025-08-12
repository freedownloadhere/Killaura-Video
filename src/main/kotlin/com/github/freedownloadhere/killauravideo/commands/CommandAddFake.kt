package com.github.freedownloadhere.killauravideo.commands

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.modules.FakeBlocks
import com.github.freedownloadhere.killauravideo.utils.Chat
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class CommandAddFake: Command()
{
    override fun getCommandName(): String = "addfake"

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if(args?.size != 3)
            throw CommandException("Needs 3 arguments x y z")

        val fakeblocks = GlobalManager.client?.moduleMap?.module("fakeblocks") as FakeBlocks?
            ?: throw CommandException("Module does not exist")

        val x = args[0].toInt()
        val y = args[1].toInt()
        val z = args[2].toInt()

        fakeblocks.addFake(x, y, z)
        Chat.info(fakeblocks, "Added block at $x $y $z")
    }
}