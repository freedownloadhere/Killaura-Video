package com.github.freedownloadhere.killauravideo.utils

import com.github.freedownloadhere.killauravideo.interfaces.IDestructible
import com.github.freedownloadhere.killauravideo.interfaces.IInitializable
import com.github.freedownloadhere.killauravideo.modules.Module

class ModuleMap(vararg modules: Module): IInitializable, IDestructible
{
    private val moduleMap = modules.associateBy { it.name.lowercase() }

    fun allModules(): Collection<Module> = moduleMap.values

    fun allNames(): Collection<String> = moduleMap.keys

    fun module(name: String) = moduleMap[name.lowercase()]

    override fun init() {
        for(module in allModules())
            module.init()
    }

    override fun destroy() {
        for(module in allModules())
            module.destroy()
    }
}