package xueluoanping.cuisine.plugin.jei.category;

import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.Cuisine;

import static xueluoanping.cuisine.Cuisine.MODID;

public class ExtendedCraftingCategory implements ICraftingCategoryExtension {
	public static final ResourceLocation UID = Cuisine.rl( "extended_crafting");


	@Override
	public @Nullable ResourceLocation getRegistryName() {
		return UID;
	}

//	@Override
//	public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
//		builder.addSlot(RecipeIngredientRole.INPUT, 0, 0)
//						.addItemStacks(List.of())
//		builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 9).addIngredient(VanillaTypes.FLUID, recipe.getResult().get(0));
//
//		ICraftingCategoryExtension.super.setRecipe(builder, craftingGridHelper, focuses);
//	}



//	@Override
//	public void setRecipe(IRecipeLayoutBuilder builder, DebugRecipe recipe, IFocusGroup focuses) {
//		IRecipeSlotBuilder inputSlot = builder.addSlot(RecipeIngredientRole.INPUT, 0, 0)
//				.addItemStacks(List.of(
//						new ItemStack(Items.BUCKET),
//						new ItemStack(Items.WATER_BUCKET),
//						new ItemStack(Items.LAVA_BUCKET),
//						new ItemStack(Items.POWDER_SNOW_BUCKET),
//						new ItemStack(Items.AXOLOTL_BUCKET),
//						new ItemStack(Items.SALMON_BUCKET),
//						new ItemStack(Items.COD_BUCKET),
//						new ItemStack(Items.PUFFERFISH_BUCKET),
//						new ItemStack(Items.TROPICAL_FISH_BUCKET)
//				));
//
//		IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 20, 0)
//				.addItemStack(ItemStack.EMPTY)
//				.addIngredients(VanillaTypes.FLUID, List.of(
//						new FluidStack(Fluids.WATER, FluidAttributes.BUCKET_VOLUME),
//						new FluidStack(Fluids.LAVA, FluidAttributes.BUCKET_VOLUME)
//				))
//				.addItemStacks(List.of(
//						new ItemStack(Items.SNOW_BLOCK),
//						new ItemStack(Items.AXOLOTL_SPAWN_EGG),
//						new ItemStack(Items.SALMON),
//						new ItemStack(Items.COD),
//						new ItemStack(Items.PUFFERFISH),
//						new ItemStack(Items.TROPICAL_FISH)
//				));
//
//		builder.createFocusLink(inputSlot, outputSlot);
//	}
}
