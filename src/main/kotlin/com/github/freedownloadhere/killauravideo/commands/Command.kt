package com.github.freedownloadhere.killauravideo.commands

import com.github.freedownloadhere.killauravideo.interfaces.INamed
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

abstract class Command: CommandBase(), INamed
{
    override val name: String
        get() = commandName
    override fun getCommandUsage(sender: ICommandSender?): String = ""
    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true
}