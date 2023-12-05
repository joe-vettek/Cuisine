package xueluoanping.cuisine.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.plugin.jei.category.SqueezingCategory;
import xueluoanping.cuisine.plugin.jei.interpreter.BasinInterpreter;
import xueluoanping.cuisine.plugin.jei.interpreter.JuiceInterpreter;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.FluidRegister;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static xueluoanping.cuisine.Cuisine.MODID;


@JeiPlugin
public class JEICompact implements IModPlugin {
	public static final ResourceLocation VANILLA_RECIPE_GUI = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
	public static final ResourceLocation CUISINE_RECIPE_GUI = new ResourceLocation(Cuisine.MODID, "textures/gui/jei.png");
	public static final ResourceLocation main = new ResourceLocation("kiwi", "retexture");
	public static final ResourceLocation UID = new ResourceLocation(MODID, "jei_plugin");
	@Nullable
	private SqueezingCategory squeezingCategory;

	public static Logger logger = LogManager.getLogger();

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	public mezz.jei.api.recipe.RecipeType<BasinSqueezingRecipe> squeezingType = new mezz.jei.api.recipe.RecipeType<>(SqueezingCategory.UID, BasinSqueezingRecipe.class);

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		logger.info("Hello" + "registerItemSubtypes");
		//        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModContents.itemBasin, BasinInterpreter.INSTANCE);
		registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, BlockEntityRegister.hide_fire_pit_item.get(), BasinInterpreter.INSTANCE);
		registration.registerSubtypeInterpreter(ForgeTypes.FLUID_STACK, FluidRegister.juice.get(), JuiceInterpreter.INSTANCE);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		assert Minecraft.getInstance().level != null;
		RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
		List<CraftingRecipe> unhandledCraftingRecipes = manager.getAllRecipesFor(RecipeType.CRAFTING);
		List<CraftingRecipe> specialCraftingRecipes = replaceSpecialCraftingRecipes(unhandledCraftingRecipes);
		//		logger.info(MODID +"test the length"+ specialCraftingRecipes.size());
		//		registration.addRecipes(specialCraftingRecipes, VanillaRecipeCategoryUid.CRAFTING);
		registration.addRecipes(squeezingType,
				manager.getAllRecipesFor(RecipeRegister.squeezingType.get()));
	}

	/**
	 * @author:mezz By default, JEI can't handle special recipes.
	 * This method expands some special unhandled recipes into a list of normal recipes that JEI can understand.
	 * <p>
	 * If a special recipe we know how to replace is not present (because it has been removed),
	 * we do not replace it.
	 */
	private static List<CraftingRecipe> replaceSpecialCraftingRecipes(List<CraftingRecipe> validRecipes) {
		Map<Class<? extends CraftingRecipe>, Supplier<Stream<CraftingRecipe>>> replacers = new IdentityHashMap<>();
		//		replacers.put(RetextureRecipe.class, basinRetextureMaker::createRecipes);

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
		registration.addRecipeCategories(
				squeezingCategory = new SqueezingCategory(guiHelper));
	}


	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(BlockEntityRegister.basin_item.get().getDefaultInstance(), squeezingType);
		BlockEntityRegister.basinItemColored.forEach(itemRegistryObject ->
				registration.addRecipeCatalyst(itemRegistryObject.get().getDefaultInstance(), squeezingType));
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registration) {

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
	}

	//    public static IRecipeSlotTooltipCallback identifierTooltip(FluidStack stack) {
	//        return (view, tooltips) -> {
	//            tooltips.add(new TextComponent(I18n.get(stack.getTranslationKey())));
	//            Cuisine.logger(tooltips.get(0) + "hello");
	//
	//        };
	//    }
}
