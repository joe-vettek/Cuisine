package xueluoanping.cuisine.data;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.ConfigCondition;
import xueluoanping.cuisine.config.General;
import xueluoanping.cuisine.data.recipe.OtherModRecipe;
import xueluoanping.cuisine.data.recipe.SqueezingRecipeGen;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.ItemRegister;

public class RecipeDataProvider extends RecipeProvider {
	public RecipeDataProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		SqueezingRecipeGen.register(consumer);
		registerCraftRecipe(consumer);
		registerSmeltingRecipe(consumer);
		controlRecipe(consumer);
		OtherModRecipe.register(consumer);
	}

	private void controlRecipe(Consumer<FinishedRecipe> consumer) {

		ConditionalRecipe.builder()
				.addCondition(new ConfigCondition(General.class.getDeclaredFields()[3].getName()))
				.addRecipe(
						ShapedRecipeBuilder.shaped(Items.BREAD)
								.define('#', Items.WHEAT)
								.pattern("###")
								.unlockedBy("has_wheat", has(Items.WHEAT))
								::save
				)
				.generateAdvancement()
				.build(consumer, new ResourceLocation("bread"));
	}

	private void registerCraftRecipe(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(BlockEntityRegister.basin_item.get())
				.pattern("I I")
				.pattern("GIG")
				.define('I', ItemTags.PLANKS)
				.define('G', Items.IRON_BARS)
				.unlockedBy("has_planks", has(ItemTags.PLANKS))
				.save(consumer);


		ShapedRecipeBuilder.shaped(ItemRegister.wooden_handle.get())
				.pattern(" I")
				.pattern("I ")
				.define('I', Items.STICK)
				.unlockedBy("has_sticks", has(Items.STICK))
				.save(consumer);

		ShapedRecipeBuilder.shaped(ItemRegister.wooden_arm.get())
				.pattern("   ")
				.pattern(" G ")
				.pattern("I  ")
				.define('I', ItemRegister.wooden_handle.get())
				.define('G', Items.SLIME_BALL)
				.unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
				.save(consumer);

		ShapedRecipeBuilder.shaped(BlockEntityRegister.mill_item.get())
				.pattern("I  ")
				.pattern("GGG")
				.pattern("GGG")
				.define('I', ItemRegister.wooden_handle.get())
				.define('G', Tags.Items.STONE)
				.unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
				.save(consumer);

		ShapelessRecipeBuilder.shapeless( ItemRegister.dough.get())
				.requires(Items.WATER_BUCKET)
				.requires( ItemRegister.flour.get())
				.unlockedBy("has_flour", has(ItemRegister.flour.get()))
				.save(consumer);

		// 厨具
		ShapedRecipeBuilder.shaped(ItemRegister.kitchen_knife.get())
				.pattern("   ")
				.pattern(" G ")
				.pattern("I  ")
				.define('I', ItemRegister.wooden_handle.get())
				.define('G', Tags.Items.INGOTS_IRON)
				.unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
				.save(consumer);

		ShapedRecipeBuilder.shaped(ItemRegister.iron_spatula.get())
				.pattern("   ")
				.pattern(" G ")
				.pattern(" I ")
				.define('I', ItemRegister.wooden_handle.get())
				.define('G', Tags.Items.INGOTS_IRON)
				.unlockedBy("has_wooden_handle", has(ItemRegister.wooden_handle.get()))
				.save(consumer);
	}

	private void registerSmeltingRecipe(Consumer<FinishedRecipe> consumer) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegister.bamboo_item.get()),
						ItemRegister.bamboo_charcoal.get(),
						0.35F, 100)
				.unlockedBy("has_bamboo", has(BlockRegister.bamboo_item.get()))
				.save(consumer, ItemRegister.bamboo_charcoal.get().getRegistryName()+"_smelt");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegister.dough.get()),
						Items.BREAD,
						0.35F, 100)
				.unlockedBy("has_dough", has(ItemRegister.dough.get()))
				.save(consumer, Cuisine.MODID+":"+Items.BREAD.getRegistryName().getPath()+"_smelt");


	}
}
