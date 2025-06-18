package com.github.freedownloadhere.killauravideo.ui.core.layout

import com.github.freedownloadhere.killauravideo.ui.core.hierarchy.IParent

interface ILayout {
    fun applyLayout() {
        if(this is ILayoutPre)
            layoutPreCallback()
        if(this is IParent)
            for(child in children)
                if(child is ILayout)
                    child.applyLayout()
        if(this is ILayoutPost)
            layoutPostCallback()
    }
}