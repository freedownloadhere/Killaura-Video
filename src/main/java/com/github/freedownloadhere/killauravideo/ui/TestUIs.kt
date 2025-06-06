package com.github.freedownloadhere.killauravideo.ui

private val loremIpsumWords = listOf("Lorem", "Ipsum", "Dolor", "Sit", "Amet")

private fun loremIpsumText(words: Int): String {
    val wordList = List(words) { loremIpsumWords.random() }
    return wordList.joinToString(" ")
}

