package com.github.freedownloadhere.killauravideo.commands

import com.github.freedownloadhere.killauravideo.GlobalManager
import com.github.freedownloadhere.killauravideo.utils.Chat
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos

class CommandToggle : CommandBase() {
    override fun getCommandName(): String = "toggle"

    override fun getCommandUsage(sender: ICommandSender?): String = ""

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true

    override fun addTabCompletionOptions(
        sender: ICommandSender?,
        args: Array<out String>?,
        pos: BlockPos?
    ): MutableList<String> {
        return GlobalManager.clientInstance?.moduleMap?.allNames()?.toMutableList() ?: mutableListOf()
    }

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        if(args.isNullOrEmpty()) {
            Chat.error("Command requires at least one module name")
            return
        }

        for(name in args.toSet())
            GlobalManager.clientInstance?.toggleModule(name)
    }
}