package xueluoanping.cuisine.data.loot;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class CuisineLootTableProvider extends LootTableProvider {

    private final PackOutput generator;

    public CuisineLootTableProvider(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator, Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                CuisineBlockLootTables::new,
                // Loot table generator for the 'empty' param set
                LootContextParamSets.BLOCK
        )),lookupProvider);
        this.generator = generator;

    }

}
