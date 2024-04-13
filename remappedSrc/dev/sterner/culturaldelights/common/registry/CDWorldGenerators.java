package dev.sterner.culturaldelights.common.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CDWorldGenerators {

    public static final ResourceKey<ConfiguredFeature<?, ?>> AVOCADO = FeatureUtils.createKey("culturaldelights:avocado");
    public static final ResourceKey<PlacedFeature> TREE_AVOCADO = PlacementUtils.createKey("culturaldelights:tree_avocado");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AVOCADO_PIT = FeatureUtils.createKey("culturaldelights:avocado_pit");
    public static final ResourceKey<PlacedFeature> TREE_AVOCADO_PIT = PlacementUtils.createKey("culturaldelights:tree_avocado_pit");

    public static void init() {
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION, TREE_AVOCADO);
    }
}
