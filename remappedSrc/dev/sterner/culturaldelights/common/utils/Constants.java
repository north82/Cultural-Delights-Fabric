package dev.sterner.culturaldelights.common.utils;

import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final String MOD_ID = "culturaldelights";

    public static ResourceLocation id(String string){
        return new ResourceLocation(MOD_ID, string);
    }
}
