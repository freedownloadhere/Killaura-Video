package com.github.freedownloadhere.killauravideo.modules

import com.lukflug.panelstudio.setting.ICategory

class ModuleMap(
    vararg categories: Pair<String, List<Module>>
) {
    private val categoryMap = categories
        .map { Category(it.first, it.second) }
        .associateBy { it.displayName }

    private val moduleMap = categories
        .flatMap { it.second }
        .associateBy { it.name.lowercase() }

    fun allCategories(): Collection<ICategory> = categoryMap.values

    fun allModules(): Collection<Module> = moduleMap.values

    fun allNames(): Collection<String> = moduleMap.keys

    fun module(name: String) = moduleMap[name.lowercase()]
}