package dev.sterner.culturaldelights;

import com.mojang.serialization.Codec;
import dev.sterner.culturaldelights.common.registry.CDObjects;
import dev.sterner.culturaldelights.common.registry.CDConfiguredFeatures;
import dev.sterner.culturaldelights.common.registry.CDTags;
import dev.sterner.culturaldelights.common.registry.CDWorldGenerators;
import dev.sterner.culturaldelights.common.utils.Constants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class CulturalDelights implements ModInitializer {
	public static final String MOD_ID = "culturaldelights";
	private static final ResourceLocation SQUID_LOOT_TABLE_ID = EntityType.SQUID.getDefaultLootTable();
	private static final ResourceLocation GLOW_SQUID_LOOT_TABLE_ID = EntityType.GLOW_SQUID.getDefaultLootTable();

	public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(MOD_ID));

	
	private static <P extends TreeDecorator> TreeDecoratorType<P> register(ResourceLocation id, Codec<P> codec) {
		return Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, id, new TreeDecoratorType<>(codec));
	}

	@Override
	public void onInitialize() {
		CDObjects.init();

		CDConfiguredFeatures.registerAll();
		CDWorldGenerators.init();

		BiomeModifications.addFeature(context -> context.getBiomeKey().equals(Biomes.PLAINS), GenerationStep.Decoration.VEGETAL_DECORATION,
				CDConfiguredFeatures.PATCH_WILD_CUCUMBERS.key());
		BiomeModifications.addFeature(context -> context.getBiomeKey().equals(Biomes.SWAMP), GenerationStep.Decoration.VEGETAL_DECORATION,
				CDConfiguredFeatures.PATCH_WILD_EGGPLANTS.key());
		BiomeModifications.addFeature(context -> context.getBiomeKey().equals(Biomes.SWAMP), GenerationStep.Decoration.VEGETAL_DECORATION,
				CDConfiguredFeatures.PATCH_WILD_CUCUMBERS.key());
		BiomeModifications.addFeature(context -> context.getBiomeKey().equals(Biomes.JUNGLE), GenerationStep.Decoration.VEGETAL_DECORATION,
				CDConfiguredFeatures.PATCH_WILD_EGGPLANTS.key());

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
			factories.add(new EmeraldToItemOffer(new ItemStack(CDObjects.CUCUMBER), 1, 10, 2, 0.2F));
			factories.add(new EmeraldToItemOffer(new ItemStack(CDObjects.EGGPLANT), 1, 10, 2, 0.2F));
			factories.add(new EmeraldToItemOffer(new ItemStack(CDObjects.CORN_COB), 1, 10, 2, 0.2F));
			factories.add(new EmeraldToItemOffer(new ItemStack(CDObjects.AVOCADO), 1, 10, 2, 0.2F));
		});

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if(source.isBuiltin() && SQUID_LOOT_TABLE_ID.equals(id)){
				LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(CDObjects.SQUID));
				tableBuilder.withPool(poolBuilder);
			}
			if(source.isBuiltin() && GLOW_SQUID_LOOT_TABLE_ID.equals(id)){
				LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(CDObjects.GLOW_SQUID));
				tableBuilder.withPool(poolBuilder);
			}
		});

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
				.icon(() -> new ItemStack(CDObjects.AVOCADO))
				.title(Component.translatable(MOD_ID + ".group.main"))
				.build());

		CDTags.init();
	}

	public static class EmeraldToItemOffer implements VillagerTrades.ItemListing {

		private final ItemStack sell;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public EmeraldToItemOffer(ItemStack stack, int price, int maxUses, int experience, float multiplier) {
			this.sell = stack;
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.price + random.nextInt(3)), sell, this.maxUses, this.experience, this.multiplier);
		}
	}
}
