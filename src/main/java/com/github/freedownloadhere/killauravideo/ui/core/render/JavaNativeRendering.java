package com.github.freedownloadhere.killauravideo.ui.core.render;

public class JavaNativeRendering {
    public static native void nInit(
        float screenWidth, float screenHeight
    );

    public static native void nUpdateScreenSize(
        float screenWidth, float screenHeight
    );

    public static native void nDrawRect(
        float x, float y, float z,
        float width, float height,
        int baseColorARGB, int borderColorARGB,
        float rounding, float bordering
    );

    public static native void nDrawText(
        String text,
        float x, float y, float z,
        int colorARGB,
        int scaleIdx
    );

    public static native float nGetTextWidth(
        String text, int scaleIdx
    );
}