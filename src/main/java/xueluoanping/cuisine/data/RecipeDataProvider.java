package xueluoanping.cuisine.data;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import xueluoanping.cuisine.data.recipe.SqueezingRecipeGen;
import xueluoanping.cuisine.register.ModContents;

public class RecipeDataProvider extends RecipeProvider {
    public RecipeDataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SqueezingRecipeGen.register(consumer);
        registerCraftRecipe(consumer);
    }

    private void registerCraftRecipe(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModContents.itemBasin)
                .pattern("I I")
                .pattern("GIG")
                .define('I', ItemTags.PLANKS)
                .define('G', Items.IRON_BARS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
    }
}
