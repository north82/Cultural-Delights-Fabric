package dev.sterner.culturaldelights.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CDTags {
    public static final TagKey<Item> CORN = cItemTag("foods/vegetables/corn");
    public static final TagKey<Item> CUCUMBER = cItemTag("foods/vegetables/cucumber");
    public static final TagKey<Item> TORTILLAS = cItemTag("foods/tortillas");

    public static final TagKey<Item> FOODS_RAW_FISHES_PUFFERFISH = cItemTag("foods/raw_fishes/pufferfish");
    public static final TagKey<Item> FOODS_GLOW_SQUID = cItemTag("foods/glow_squid");
    public static final TagKey<Item> FOODS_SQUID = cItemTag("foods/squid");

    private static TagKey<Item> cItemTag(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", path));
    }

    public static void init() {
    }
}
