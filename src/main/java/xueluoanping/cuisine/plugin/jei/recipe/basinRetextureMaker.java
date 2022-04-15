package xueluoanping.cuisine.plugin.jei.recipe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class basinRetextureMaker {

	public static Logger logger = LogManager.getLogger();
//	public static Stream<CraftingRecipe> createRecipes() {
//		String group = "kiwi.cuisine.retxture";
//		return
//				NBTUtils.getBlockListFromRegister().stream()
//						.map(block -> {
//							ItemStack ironBars_Stack = new ItemStack(Items.IRON_BARS);
//							ItemStack basin_RawMaterial_Stack = new ItemStack(block);
//							Ingredient ironBars_Ingredient = Ingredient.of(ironBars_Stack);
//							Ingredient basin_RawMaterial_Ingredient = new NBTIngredient(basin_RawMaterial_Stack) {};
//							NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
//									Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY,
//									basin_RawMaterial_Ingredient, Ingredient.EMPTY, basin_RawMaterial_Ingredient,
//									ironBars_Ingredient, basin_RawMaterial_Ingredient, ironBars_Ingredient
//							);
////							logger.info(block.getRegistryName());
//							ItemStack output = NBTUtils.createTagFromTxtureProvider(new ItemStack(CommonModule.Basin),block) ;
//							ResourceLocation id = new ResourceLocation(MODID, group + "." + output.getDescriptionId() + "." + block.getDescriptionId());
//							return new ShapedRecipe(id, group, 3, 3, inputs, output);
//						});
//	}


}
