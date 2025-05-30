package com.github.freedownloadhere.killauravideo.ui.basic

import com.github.freedownloadhere.killauravideo.ui.implementations.TextDraw
import com.github.freedownloadhere.killauravideo.ui.implementations.TextLayout
import com.github.freedownloadhere.killauravideo.ui.interfaces.layout.ILayoutPost
import com.github.freedownloadhere.killauravideo.ui.interfaces.misc.IText
import com.github.freedownloadhere.killauravideo.ui.interfaces.render.IDrawable

class UIText(
    defaultText: String = "Text"
)   : UI(),
    IText,
    IDrawable by TextDraw<UIText>(),
    ILayoutPost by TextLayout<UIText>()
{
    override val scale: IText.Scale = IText.Scale.MEDIUM
    var source: () -> String = { defaultText }

    override val text: String
        get() = source()
}
