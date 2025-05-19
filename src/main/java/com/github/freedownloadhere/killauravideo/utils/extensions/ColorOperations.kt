package com.github.freedownloadhere.killauravideo.utils.extensions

import java.awt.Color

fun Color.toPackedARGB(): Int = 0 or (alpha shl 24) or (red shl 16) or (green shl 8) or blue