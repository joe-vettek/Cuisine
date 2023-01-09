package xueluoanping.cuisine.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.tag.ForgeTags;

import java.util.function.Consumer;

public class OtherModRecipe {
    public static void register(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(CropRegister.chinese_cabbage_item.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.CABBAGE_LEAF.get(), 2)
                .build(consumer);
        // CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ForgeTags.VEGETABLES_CABBAGE), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.CABBAGE_LEAF.get(), 2)
        //         .build(consumer);
    }
}
