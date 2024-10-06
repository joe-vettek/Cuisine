package xueluoanping.cuisine.data.loot;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CuisineBlockLootTables extends BlockLootSubProvider {

    public CuisineBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return map.entrySet()
                .stream()
                .map(e -> BuiltInRegistries.BLOCK.stream()
                        .filter(block -> block.getLootTable().equals(e.getKey()))
                        .findFirst()
                        .get())
                .toList();
    }


    protected void dropSelfWithContents(Set<Block> blocks) {
        for (Block block : blocks) {
            // if (skipBlocks.contains(block)) {
            //     continue;
            // }
            add(block, createSingleItemTable(block));
        }
    }

    @Override
    protected void generate() {
        Set<Block> blocks = BuiltInRegistries.BLOCK.stream()
                .filter(block -> Cuisine.MODID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace())).collect(Collectors.toSet())
                .stream().filter(block -> !block.getLootTable().equals(BuiltInLootTables.EMPTY))
                .collect(Collectors.toSet());
        blocks.remove(BlockEntityRegister.basin.get());
        blocks.remove(BlockRegister.bamboo_plant.get());

        dropSelfWithContents(blocks);
        CropRegister.getAllCropBlocks().forEach(blockRegistryObject -> {
            LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(blockRegistryObject.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockCuisineCrops.AGE, 7));

            if (!(blockRegistryObject.get() instanceof BlockDoubleCrops)) {
                add(blockRegistryObject.get(), createCropDrops(blockRegistryObject.get(),
                        blockRegistryObject.get().asItem(),
                        blockRegistryObject.get().asItem(),
                        lootitemcondition$builder));
            } else {
                LootItemCondition.Builder
                        lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(blockRegistryObject.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockDoubleCrops.HALF, DoubleBlockHalf.LOWER));
                LootItemCondition.Builder
                        lootitemcondition$builder3 = LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(blockRegistryObject.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockDoubleCrops.HALF, DoubleBlockHalf.UPPER));
                add(blockRegistryObject.get(), createDoubleCropDrops(blockRegistryObject.get(),
                        blockRegistryObject.get().asItem(),
                        blockRegistryObject.get().asItem(),
                        lootitemcondition$builder, lootitemcondition$builder2, lootitemcondition$builder3));
            }

            ;
        });
        // super.addTables();
    }


    protected LootTable.Builder createDoubleCropDrops(
            Block block, Item result, Item seed,
            LootItemCondition.Builder ageBuilder,
            LootItemCondition.Builder lowerControl,
            LootItemCondition.Builder upperControl) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(seed).when(lowerControl))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(2.0F))
                        .add(LootItem.lootTableItem(result)
                                .when(ageBuilder)
                                .when(upperControl)))
                // .otherwise()))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .setBonusRolls(ConstantValue.exactly(3.0F))
                        .when(ageBuilder)
                        .when(upperControl)
                        .add(LootItem.lootTableItem(seed)
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3))));
    }

}
