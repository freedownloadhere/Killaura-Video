package com.github.freedownloadhere.killauravideo.utils

import java.io.File
import java.io.FileNotFoundException

object LibraryLoading {
    private var loaded: Boolean = false

    private const val LIB_NAME = "JavaNativeRendering.dll"
    private const val LIB_PATH = "/assets/killauravideo/natives/$LIB_NAME"

    fun load() {
        assert(!loaded) { "The native libraries are already loaded!" }

        val libraryInputStream = LibraryLoading::class.java.getResourceAsStream(LIB_PATH)
            ?: throw FileNotFoundException("Failed to find $LIB_PATH")

        val libraryBytes = libraryInputStream.readBytes()
        libraryInputStream.close()

        val tempFile = File.createTempFile("killauravideo-", LIB_NAME)
        tempFile.writeBytes(libraryBytes)

        System.load(tempFile.absolutePath)

        tempFile.delete()
        loaded = true
    }
}