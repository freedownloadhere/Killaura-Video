package com.github.freedownloadhere.killauravideo.ui.core.render;

import java.awt.*;

public class JavaNativeRendering {
    public static native void nInit(
        float screenWidth, float screenHeight
    );

    public static native void nDrawRect(
        float x, float y, float z,
        float width, float height,
        Color baseColor, Color borderColor,
        float rounding, float bordering
    );

    public static native void nDrawText(
        String text,
        float x, float y, float z,
        Color color,
        float scale
    );
}
