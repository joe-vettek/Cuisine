package xueluoanping.cuisine.data.loot;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import xueluoanping.cuisine.loot.AddItemModifier;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.GlobalLootModifierRegistry;

import java.util.ArrayList;
import java.util.List;

public class GLMProvider extends GlobalLootModifierProvider {
    public GLMProvider(DataGenerator gen, String modid) {
        super(gen, modid);
    }

    @Override
    protected void start() {
        CropRegister.getGrassCrops().forEach(itemRegistryObject -> {
            List<ItemStack> resultList=new ArrayList<>();
            resultList.add(itemRegistryObject.get().getDefaultInstance());
            this.add(itemRegistryObject.get().getRegistryName().getPath()+"_from_grass",
                    GlobalLootModifierRegistry.add_item.get(), new AddItemModifier(
                    new LootItemCondition[] {
                            LootItemRandomChanceCondition.randomChance(0.1f).build(),
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                            InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))).build()
                    },itemRegistryObject.get(),1
            ));
            this.add(itemRegistryObject.get().getRegistryName().getPath()+"_from_grass",
                    GlobalLootModifierRegistry.add_item.get(), new AddItemModifier(
                            new LootItemCondition[] {
                                    LootItemRandomChanceCondition.randomChance(0.1f).build(),
                                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                                    InvertedLootItemCondition.invert(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))).build()
                            },itemRegistryObject.get(),1
                    ));
        });

    }
}
