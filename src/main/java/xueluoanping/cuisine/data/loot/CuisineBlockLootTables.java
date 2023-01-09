package xueluoanping.cuisine.data.loot;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.Blaze3D;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CuisineBlockLootTables extends BlockLoot {

    // I‘m not sure if I need a map for myself, but as you see the BlockLoot class have a same one
    // so while you add your block, and then you need to deal with the block you don't need
    // If you don't want to do some extra check, maybe my method is better,
    // so now you need override add,accept method yourself. Don't be lazy.
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();
    // private final Set<Block> skipBlocks = new HashSet<>();

    // public CuisineBlockLootTables() {
    //     // skip(BlockRegister.bamboo_plant.get());
    // }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.map.put(block.getLootTable(), builder);
        // super.add(block, builder);
        // skipBlocks.add(block);
        // skip(block);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        // Cuisine.logger(super.getKnownBlocks());
        // return new ArrayList<>() ;
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> Optional.ofNullable(block.getRegistryName())
                        .filter(registryName -> registryName.getNamespace().equals(Cuisine.MODID)).isPresent()
                ).collect(Collectors.toList());
        // return CropRegister.DRBlocks.getEntries() // Get all registered entries
        //         .stream() // Stream the wrapped objects
        //         .flatMap(RegistryObject::stream) // Get the object if available
        //         ::iterator; // Create the iterable
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
    protected void addTables() {
        Set<Block> blocks = new HashSet<>(Registry.BLOCK.stream()
                .filter(block -> Cuisine.MODID.equals(Registry.BLOCK.getKey(block).getNamespace()))
                .collect(Collectors.toSet()));
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
                        lootitemcondition$builder, lootitemcondition$builder2,lootitemcondition$builder3));
            }

            ;
        });
        // super.addTables();
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        // super.accept(consumer);
        this.addTables();
        Set<ResourceLocation> set = Sets.newHashSet();

        for (Block block : getKnownBlocks()) {
            ResourceLocation resourcelocation = block.getLootTable();
            if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
                LootTable.Builder loottable$builder = this.map.remove(resourcelocation);
                if (loottable$builder == null) {
                    // throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, Registry.BLOCK.getKey(block)));
                    Cuisine.logger(String.format("Missing loottable '%s' for '%s'", resourcelocation, Registry.BLOCK.getKey(block)));
                }
                try {
                    consumer.accept(resourcelocation, loottable$builder);
                } catch (Exception e) {
                    Cuisine.logger(e.getMessage());
                    // e.printStackTrace();
                }
            }
        }

        if (!this.map.isEmpty()) {
            // throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
            Cuisine.logger("Created block loot tables for non-blocks: " + this.map.keySet());
        }
    }

    protected static LootTable.Builder createDoubleCropDrops(
            Block block, Item result, Item seed,
            LootItemCondition.Builder ageBuilder,
            LootItemCondition.Builder lowerControl,
            LootItemCondition.Builder upperControl) {
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
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))));
    }

}
