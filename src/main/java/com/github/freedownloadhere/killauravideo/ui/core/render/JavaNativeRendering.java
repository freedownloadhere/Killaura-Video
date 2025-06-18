package com.github.freedownloadhere.killauravideo.ui.core.render;

public class JavaNativeRendering {
    public static native void nInit(
        float screenWidth, float screenHeight
    );

    public static native void nUpdateScreenSize(
        float screenWidth, float screenHeight
    );

    public static native void nAddRectToMesh(
        float x, float y, float z,
        float width, float height,
        int baseColorARGB, int borderColorARGB,
        float rounding, float bordering,
        int textureID
    );

    public static native void nAddTextToMesh(
        String text,
        float x, float y, float z,
        int colorARGB,
        int scaleIdx
    );

    public static native void nAddLineToMesh(
        float x1, float y1, float z1,
        float x2, float y2, float z2,
        int colorARGB, float width
    );

    public static native float nGetTextWidth(
        String text, int scaleIdx
    );

    public static native int nUploadTexture(
        byte[] texBytes
    );
}