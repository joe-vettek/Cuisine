package xueluoanping.cuisine.data.loot;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.*;

import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import xueluoanping.cuisine.loot.AddItemModifier;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.GlobalLootModifierRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GLMProvider extends GlobalLootModifierProvider {
    public GLMProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(gen, registries, modid);
    }

    @Override
    protected void start() {
        CropRegister.getGrassCrops().forEach(itemRegistryObject -> {
            List<ItemStack> resultList = new ArrayList<>();
            resultList.add(itemRegistryObject.get().getDefaultInstance());

            for (Block grass : List.of(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN)) {
                this.add(itemRegistryObject.getId().getPath() + "_from_"+ BuiltInRegistries.BLOCK.getKey(grass).getPath(),
                        new AddItemModifier(
                                new LootItemCondition[]{
                                        LootItemRandomChanceCondition.randomChance(0.1f).build(),
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(grass).build(),
                                        InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))).build()
                                }, itemRegistryObject.get(), 1
                        ));
            }

        });

    }
}
