package xueluoanping.cuisine.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import snownee.kiwi.recipe.crafting.RetextureRecipe;
import xueluoanping.cuisine.CoreModule;
import xueluoanping.cuisine.api.util.NBTUtils;
import xueluoanping.cuisine.plugin.jei.interpreter.BasinInterpreter;
import xueluoanping.cuisine.plugin.jei.recipe.basinRetextureMaker;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static xueluoanping.cuisine.Cuisine.MODID;


@JeiPlugin
public class JEICompact implements IModPlugin {

	public static final ResourceLocation main = new ResourceLocation("kiwi", "retexture");
	public static final ResourceLocation UID = new ResourceLocation(MODID, "jei_plugin");
	public static Logger logger = LogManager.getLogger();

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		logger.info("Hello" + "registerItemSubtypes");
		IIngredientSubtypeInterpreter<ItemStack> interpreter = (stack, context) -> {
			if (context == UidContext.Ingredient) {
				return NBTUtils.getNameFromRetxtureStack(stack);
			}
			return IIngredientSubtypeInterpreter.NONE;
		};
		registration.registerSubtypeInterpreter(CoreModule.Basin.asItem(), BasinInterpreter.INSTANCE);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		assert Minecraft.getInstance().level != null;
		RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
		List<CraftingRecipe> unhandledCraftingRecipes = manager.getAllRecipesFor(RecipeType.CRAFTING);
		List<CraftingRecipe> specialCraftingRecipes = replaceSpecialCraftingRecipes(unhandledCraftingRecipes);
//		logger.info(MODID +"test the length"+ specialCraftingRecipes.size());
		registration.addRecipes(specialCraftingRecipes, VanillaRecipeCategoryUid.CRAFTING);
	}

	/**
	 * @author:mezz
	 * By default, JEI can't handle special recipes.
	 * This method expands some special unhandled recipes into a list of normal recipes that JEI can understand.
	 * <p>
	 * If a special recipe we know how to replace is not present (because it has been removed),
	 * we do not replace it.
	 */
	private static List<CraftingRecipe> replaceSpecialCraftingRecipes(List<CraftingRecipe> validRecipes) {
		Map<Class<? extends CraftingRecipe>, Supplier<Stream<CraftingRecipe>>> replacers = new IdentityHashMap<>();
		replacers.put(RetextureRecipe.class, basinRetextureMaker::createRecipes);

		return validRecipes.parallelStream()
				.map(CraftingRecipe::getClass)
				.distinct()
				.filter(replacers::containsKey)
				// distinct + this limit will ensure we stop iterating early if we find all the recipes we're looking for.
				.limit(replacers.size())
				.map(replacers::get)
				.flatMap(Supplier::get)
				.toList();
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

//		registration.addRecipeCategories(
//				craftingCategory = new CraftingRecipeCategory(guiHelper));
	}


	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registration) {

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
	}
}
