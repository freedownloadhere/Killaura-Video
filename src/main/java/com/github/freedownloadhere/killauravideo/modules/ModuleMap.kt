package com.github.freedownloadhere.killauravideo.modules

class ModuleMap(
    vararg modules: Module
) {
    private val moduleMap = modules.associateBy { it.name.lowercase() }

    fun allModules(): Collection<Module> = moduleMap.values

    fun module(name: String) = moduleMap[name.lowercase()]
}