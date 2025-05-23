package com.github.freedownloadhere.killauravideo.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FontRenderer.class)
public interface AccessorFontRenderer {
    @Accessor("locationFontTexture")
    ResourceLocation getFontLocation_killauravideo();
}
