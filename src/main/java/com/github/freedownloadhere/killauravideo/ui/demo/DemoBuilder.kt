package com.github.freedownloadhere.killauravideo.ui.demo

import com.github.freedownloadhere.killauravideo.utils.Chat

class DemoBuilder {
    private fun testParagraph() : String { var str = ""; for(i in 1..100) str += "Lorem Ipsum Dolor Sit Amet "; return str }

    val base =
        WindowBuilder("Demo Window Test")
            .header("This is the window list")
            .button("A test button") { Chat.addMessage("UI Test", "This is a button callback message") }
            .textBox("This is a textbox")
            .beginList()
                .header("This is a sublist")
                .paragraph(testParagraph())
                .beginList()
                    .textBox("This is a textbox")
                    .header("Another sublist of the sublist")
                    .button("Another test button") { Chat.addMessage("UI Test", "This is a button callback message") }
                    .paragraph(testParagraph())
                    .button("yet another test button") { Chat.addMessage("UI Test", "This is a button callback message") }
                .endList()
                .paragraph(testParagraph())
            .endList()
            .beginList()
                .header("Second sublist")
                .paragraph(testParagraph())
                .beginList()
                    .header("Another sublist of the sublist")
                    .paragraph(testParagraph())
                    .textBox("This is a textbox")
                .endList()
                .paragraph(testParagraph())
            .endList()
            .finish()
}