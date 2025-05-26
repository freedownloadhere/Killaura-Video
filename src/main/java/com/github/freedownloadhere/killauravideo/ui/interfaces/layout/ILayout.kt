package com.github.freedownloadhere.killauravideo.ui.interfaces.layout

import com.github.freedownloadhere.killauravideo.ui.interfaces.parents.IParent

interface ILayout {
    fun applyLayout() {
        if(this is ILayoutPre)
            applyLayoutPre()
        if(this is IParent)
            for(child in children)
                if(child is ILayout)
                    child.applyLayout()
        if(this is ILayoutPost)
            applyLayoutPost()
    }
}