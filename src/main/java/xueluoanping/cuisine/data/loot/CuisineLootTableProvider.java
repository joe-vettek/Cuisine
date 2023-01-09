package xueluoanping.cuisine.data.loot;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class CuisineLootTableProvider extends LootTableProvider {
    public CuisineLootTableProvider(DataGenerator p_124437_) {
        super(p_124437_);
    }

    @Override
    public String getName() {
        return "Cuisine Loot-Table Provider";
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        // Do not validate against all registered loot tables
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return Lists.newArrayList(

                // Pair.of(EssenceEntityLootTables::new, LootParameterSets.ENTITY)
                Pair.of(CuisineBlockLootTables::new, LootContextParamSets.BLOCK)
        );
        // return null;
    }
}
