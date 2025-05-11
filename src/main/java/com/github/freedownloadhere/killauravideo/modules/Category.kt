package com.github.freedownloadhere.killauravideo.modules

import com.lukflug.panelstudio.setting.ICategory
import com.lukflug.panelstudio.setting.IModule
import java.util.stream.Stream

class Category(
    private val displayName: String,
    private val moduleList: List<IModule>
) : ICategory
{
    override fun getDisplayName(): String = displayName

    override fun getModules(): Stream<IModule> = moduleList.stream()
}