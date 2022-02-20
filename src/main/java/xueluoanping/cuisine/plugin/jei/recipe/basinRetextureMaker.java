package xueluoanping.cuisine.plugin.jei.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.NBTIngredient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xueluoanping.cuisine.CoreModule;
import xueluoanping.cuisine.api.util.NBTUtils;

import java.util.stream.Stream;

import static xueluoanping.cuisine.Cuisine.MODID;


public class basinRetextureMaker {

	public static Logger logger = LogManager.getLogger();
	public static Stream<CraftingRecipe> createRecipes() {
		String group = "kiwi.cuisine.retxture";
		return
				NBTUtils.getBlockListFromRegister().stream()
						.map(block -> {
							ItemStack ironBars_Stack = new ItemStack(Items.IRON_BARS);
							ItemStack basin_RawMaterial_Stack = new ItemStack(block);
							Ingredient ironBars_Ingredient = Ingredient.of(ironBars_Stack);
							Ingredient basin_RawMaterial_Ingredient = new NBTIngredient(basin_RawMaterial_Stack) {};
							NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
									Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY,
									basin_RawMaterial_Ingredient, Ingredient.EMPTY, basin_RawMaterial_Ingredient,
									ironBars_Ingredient, basin_RawMaterial_Ingredient, ironBars_Ingredient
							);
//							logger.info(block.getRegistryName());
							ItemStack output = NBTUtils.createTagFromTxtureProvider(new ItemStack(CoreModule.Basin),block) ;
							ResourceLocation id = new ResourceLocation(MODID, group + "." + output.getDescriptionId() + "." + block.getDescriptionId());
							return new ShapedRecipe(id, group, 3, 3, inputs, output);
						});
	}

	private basinRetextureMaker() {

	}
}
