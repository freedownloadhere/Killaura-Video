package com.github.freedownloadhere.killauravideo.commands

import com.github.freedownloadhere.killauravideo.Manager
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class CommandToggle : CommandBase() {
    override fun getCommandName(): String = "toggle"

    override fun getCommandUsage(sender: ICommandSender?): String = ""

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true

    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        Manager.toggleKillaura()
    }
}