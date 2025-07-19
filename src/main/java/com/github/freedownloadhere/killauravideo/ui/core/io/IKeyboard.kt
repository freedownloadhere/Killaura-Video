package com.github.freedownloadhere.killauravideo.ui.core.io

interface IKeyboard {
    enum class EventState {
        RELEASE, PRESS, REPEAT
    }

    val eventState: EventState
    val eventKey: Int
    val eventChar: Char
}