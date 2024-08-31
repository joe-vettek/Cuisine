package xueluoanping.cuisine.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.ConditionalRecipeOutput;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.ConfigCondition;
import xueluoanping.cuisine.config.General;
import xueluoanping.cuisine.data.recipe.SqueezingRecipeGen;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.ItemRegister;

public class RecipeDataProvider extends RecipeProvider {
    public RecipeDataProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        SqueezingRecipeGen.register(pRecipeOutput);
        registerCraftRecipe(pRecipeOutput);
        registerSmeltingRecipe(pRecipeOutput);
        controlRecipe(pRecipeOutput);
    }


    private void controlRecipe(RecipeOutput consumer) {

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.BREAD)
                .define('#', Items.WHEAT)
                .pattern("###")
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(consumer.withConditions(new ConfigCondition(General.class.getDeclaredFields()[3].getName())));

    }

    private void registerCraftRecipe(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockEntityRegister.basin_item.get())
                .pattern("I I")
                .pattern("GIG")
                .define('I', ItemTags.PLANKS)
                .define('G', Items.IRON_BARS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
        BlockEntityRegister.basinColored.forEach((dyeColor, blockRegistryObject) -> {
            Block block = BlockEntityRegister.colorBlockMap.get(dyeColor);
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, blockRegistryObject.get())
                    .pattern("I I")
                    .pattern("GIG")
                    .define('I', block)
                    .define('G', Items.IRON_BARS)
                    .unlockedBy("has_" + BuiltInRegistries.BLOCK.getKey(block).getPath(), has(block))
                    .save(consumer);
        });

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegister.wooden_handle.get())
                .pattern(" I")
                .pattern("I ")
                .define('I', Items.STICK)
                .unlockedBy("has_sticks", has(Items.STICK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ItemRegister.wooden_arm.get())
                .pattern("   ")
                .pattern(" G ")
                .pattern("I  ")
                .define('I', ItemRegister.wooden_handle.get())
                .define('G', Items.SLIME_BALL)
                .unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, BlockEntityRegister.mill_item.get())
                .pattern("I  ")
                .pattern("GGG")
                .pattern("GGG")
                .define('I', ItemRegister.wooden_handle.get())
                .define('G', Tags.Items.STONES)
                .unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegister.dough.get())
                .requires(Items.WATER_BUCKET)
                .requires(ItemRegister.flour.get())
                .unlockedBy("has_flour", has(ItemRegister.flour.get()))
                .save(consumer);


        // 厨具
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegister.kitchen_knife.get())
                .pattern("   ")
                .pattern(" G ")
                .pattern("I  ")
                .define('I', ItemRegister.wooden_handle.get())
                .define('G', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegister.iron_spatula.get())
                .pattern("   ")
                .pattern(" G ")
                .pattern(" I ")
                .define('I', ItemRegister.wooden_handle.get())
                .define('G', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
                .save(consumer);
    }

    private void registerSmeltingRecipe(RecipeOutput consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegister.bamboo_item.get()),
                        RecipeCategory.MISC,
                        ItemRegister.bamboo_charcoal.get(),
                        0.35F, 100)
                .unlockedBy("has_bamboo", has(BlockRegister.bamboo_item.get()))
                .save(consumer, ItemRegister.bamboo_charcoal.getId() + "_smelt");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegister.dough.get()),
                        RecipeCategory.FOOD,
                        Items.BREAD,
                        0.35F, 100)
                .unlockedBy("has_dough", has(ItemRegister.dough.get()))
                .save(consumer, Cuisine.MODID + ":" + BuiltInRegistries.ITEM.getKey(Items.BREAD).getPath() + "_smelt");


    }
}
