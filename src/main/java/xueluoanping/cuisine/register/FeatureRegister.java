package xueluoanping.cuisine.register;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.worldgen.crop.CropFarmlandFeature;
import xueluoanping.cuisine.worldgen.tree.SimpleBambooFeature;

import java.util.function.Supplier;

public class FeatureRegister {
    public static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);
    public static final BlockPos BLOCK_ABOVE = new BlockPos(0, 1, 0);

    public static final DeferredRegister<Feature<?>> DRFeatures = DeferredRegister
            .create(Registry.FEATURE_REGISTRY, Cuisine.MODID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> DRConfigured = DeferredRegister
            .create(Registry.CONFIGURED_FEATURE_REGISTRY, Cuisine.MODID);
    public static final DeferredRegister<PlacedFeature> DRPlaced = DeferredRegister
            .create(Registry.PLACED_FEATURE_REGISTRY, Cuisine.MODID);

    // 竹笋
    public static final RegistryObject<ConfiguredFeature<?, ?>> FEATURE_PATCH_BAMBOOSHOOT = DRConfigured.register("patch_bambooshoot",
            () -> getConfiguredFeature(BlockRegister.bamboo_plant, BlockTags.DIRT));
    public static final RegistryObject<ConfiguredFeature<?, ?>> FEATURE_PATCH_BAMBOO = DRConfigured.register("patch_bamboo",
            () -> getConfiguredFeature(BlockRegister.bamboo_plant, BlockTags.DIRT));
    public static final RegistryObject<PlacedFeature> PATCH_BAMBOOSHOOT = DRPlaced.register("patch_bambooshoot",
            () -> wildPlantPatch(FEATURE_PATCH_BAMBOOSHOOT, RarityFilter.onAverageOnceEvery(20),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));


    // 竹子
    public static final RegistryObject<Feature> f1 = DRFeatures.register("simple_bamboo", () -> new SimpleBambooFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final RegistryObject<ConfiguredFeature<?, ?>> f2 = DRConfigured.register("simple_bamboo_configured",
            () -> new ConfiguredFeature<>(FeatureRegister.f1.get(),
                    new ProbabilityFeatureConfiguration(10.0F)));
    public static final RegistryObject<PlacedFeature> f3 = DRPlaced.register("simple_bamboo_placed",
            () -> new PlacedFeature(Holder.direct(FeatureRegister.f2.get()),
                    Lists.newArrayList(RarityFilter.onAverageOnceEvery(20),
                            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    //    public static final Feature<ProbabilityFeatureConfiguration> BAMBOO = register("bamboo",
    //            new SimpleBambooFeature(ProbabilityFeatureConfiguration.CODEC));
    //    public static final Holder<ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> BAMBOO_SIMPLE =
    //            FeatureUtils.register("simple_bamboo", BiomeDecorateRegister.BAMBOO,
    //                    new ProbabilityFeatureConfiguration(0.0F));
    //    public static final Holder<PlacedFeature> BAMBOO_LIGHT =
    //            PlacementUtils.register("simple_bamboo_set", BiomeDecorateRegister.BAMBOO_SIMPLE,
    //                    RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    //
    //    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String p_65808_, F p_65809_) {
    //        return Registry.register(Registry.FEATURE, p_65808_, p_65809_);
    //    }

    // 生成农田或者水稻
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> crop_farmland =
            DRFeatures.register("crop_farmland",
                    () -> new CropFarmlandFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<ConfiguredFeature<?,?>> crop_farmland_configured =
            DRConfigured.register("crop_farmland_configured",
                    () -> new ConfiguredFeature<>(crop_farmland.get(),
                           new NoneFeatureConfiguration()));
    public static final RegistryObject<PlacedFeature> crop_farmland_placed =
            DRPlaced.register("crop_farmland_placed",
                    // 如果用RegistryObject.getholder.get会导致，Error loading registry data:RegistryAccess.readRegistry，很奇怪
                    // 农夫乐事和玉米乐事用的是Feature.RANDOM_PATCH，但是我用的是用自己用RegisterObject注册的Feature，这在一定程度上会变成空的
                    () -> new PlacedFeature(
                            crop_farmland_configured.getHolder().get() ,
                            // Holder.direct(crop_farmland_configured.get()),
                            Lists.newArrayList(RarityFilter.onAverageOnceEvery(20),
                                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()))
            );

    // 辅助函数
    private static ConfiguredFeature<?, ?> getConfiguredFeature(Supplier<Block> wild, TagKey<Block> blockTag) {
        return new ConfiguredFeature<>(Feature.RANDOM_PATCH, getWildCropConfiguration(wild.get(),
                64, 4, BlockPredicate.matchesTag(blockTag, BLOCK_BELOW)));
    }

    public static RandomPatchConfiguration getWildCropConfiguration(Block block, int tries, int xzSpread, BlockPredicate plantedOn) {
        return new RandomPatchConfiguration(tries, xzSpread, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
                BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
    }

    private static PlacedFeature wildPlantPatch(RegistryObject<ConfiguredFeature<?, ?>> feature,
                                                PlacementModifier... modifiers) {
        return new PlacedFeature(feature.getHolder().get(), Lists.newArrayList(modifiers));
    }

    public static Holder<PlacedFeature> plantBlockConfig(Block block, BlockPredicate plantedOn) {
        return PlacementUtils.filtered(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
                BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn));
    }

    public static Holder<PlacedFeature> floorBlockConfig(Block block, BlockPredicate replaces) {
        return PlacementUtils.filtered(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
                BlockPredicate.allOf(BlockPredicate.replaceable(BLOCK_ABOVE), replaces));
    }


}
