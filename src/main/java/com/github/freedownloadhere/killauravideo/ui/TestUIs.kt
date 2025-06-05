package com.github.freedownloadhere.killauravideo.ui

import com.github.freedownloadhere.killauravideo.ui.basic.UIText
import com.github.freedownloadhere.killauravideo.ui.composite.UIButton
import com.github.freedownloadhere.killauravideo.ui.dsl.button
import com.github.freedownloadhere.killauravideo.ui.dsl.text
import kotlin.random.Random

private val loremIpsumWords = listOf("Lorem", "Ipsum", "Dolor", "Sit", "Amet")

private fun loremIpsumText(words: Int): String {
    val wordList = List(words) { loremIpsumWords.random() }
    return wordList.joinToString(" ")
}

object TestUIs {
    val buttonMedium: UIButton
        get() = button {
            text.source = { "This is a button. ${Random.nextInt(1_000_000, 9_999_999)}" }
            clickAction = { "Medium button ${this.toString()}" }
        }

    val buttonShort: UIButton
        get() = button {
            text.source = { "${Random.nextInt(10, 100)}" }
            clickAction = { "Short button ${this.toString()}" }
        }

    val textMediumStatic: UIText
        get() = text {
            val staticText = loremIpsumText(5)
            source = { staticText }
        }

    val textShortStatic: UIText
        get() = text {
            val staticText = loremIpsumText(2)
            source = { staticText }
        }
}