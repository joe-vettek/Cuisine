package xueluoanping.cuisine.register;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.worldgen.tree.SimpleBambooFeature;

import java.util.function.Supplier;

public class BiomeDecorateRegister {
    public static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);
    public static final DeferredRegister<Feature<?>> DRFeatures = DeferredRegister
            .create(Registry.FEATURE_REGISTRY, Cuisine.MODID);

    public static final DeferredRegister<ConfiguredFeature<?, ?>> DRConfigured = DeferredRegister
            .create(BuiltinRegistries.CONFIGURED_FEATURE.key(), Cuisine.MODID);
    public static final DeferredRegister<PlacedFeature> DRPlaced = DeferredRegister
            .create(BuiltinRegistries.PLACED_FEATURE.key(), Cuisine.MODID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> FEATURE_PATCH_BAMBOOSHOOT = DRConfigured.register("patch_bambooshoot",
            () -> getConfiguredFeature(BlockRegister.bamboo_plant, BlockTags.DIRT));
    public static final RegistryObject<ConfiguredFeature<?, ?>> FEATURE_PATCH_BAMBOO = DRConfigured.register("patch_bamboo",
            () -> getConfiguredFeature(BlockRegister.bamboo_plant, BlockTags.DIRT));

    public static final RegistryObject<PlacedFeature> PATCH_BAMBOOSHOOT = DRPlaced.register("patch_bambooshoot",
            () -> wildPlantPatch(FEATURE_PATCH_BAMBOOSHOOT, RarityFilter.onAverageOnceEvery(20),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

    public static final RegistryObject<PlacedFeature> PATCH_BAMBOO = DRPlaced.register("patch_bamboo",
            () -> wildPlantPatch(FEATURE_PATCH_BAMBOO, RarityFilter.onAverageOnceEvery(20),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

    public static final RegistryObject<Feature> f1 = DRFeatures.register("simple_bamboo", () -> new SimpleBambooFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final RegistryObject<ConfiguredFeature<?, ?>> f2 = DRConfigured.register("simple_bamboo_configured",
            () -> new ConfiguredFeature<>(BiomeDecorateRegister.f1.get(),
                    new ProbabilityFeatureConfiguration(10.0F)));
    public static final RegistryObject<PlacedFeature> f3 = DRPlaced.register("simple_bamboo_placed",
            () -> new PlacedFeature(Holder.direct(BiomeDecorateRegister.f2.get()),
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


    private static ConfiguredFeature<?, ?> getConfiguredFeature(Supplier<Block> wild, TagKey<Block> blockTag) {
        return new ConfiguredFeature<>(Feature.RANDOM_PATCH, getWildCropConfiguration(wild.get(),
                64, 4, BlockPredicate.matchesTag(blockTag, BLOCK_BELOW)));
    }

    public static RandomPatchConfiguration getWildCropConfiguration(Block block, int tries, int xzSpread, BlockPredicate plantedOn) {
        return new RandomPatchConfiguration(tries, xzSpread, 3, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
                BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
    }

    private static PlacedFeature wildPlantPatch(Supplier<ConfiguredFeature<?, ?>> feature,
                                                PlacementModifier... modifiers) {
        return new PlacedFeature(Holder.direct(feature.get()), Lists.newArrayList(modifiers));
    }

}
