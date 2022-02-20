package xueluoanping.cuisine.api;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.loader.event.InitEvent;
import snownee.kiwi.recipe.AlternativesIngredientSerializer;
import snownee.kiwi.recipe.FullBlockIngredient;
import snownee.kiwi.recipe.crafting.RetextureRecipe;
import xueluoanping.cuisine.api.recipe.ReTextureShapedRecipe;

@KiwiModule.Optional
@KiwiModule(value = "api")
public class APIModule extends AbstractModule {

	public static final RecipeSerializer<ReTextureShapedRecipe> RETEXTURE2 = new xueluoanping.cuisine.api.recipe.ReTextureShapedRecipe.Serializer();

//	protected void init(InitEvent event) {
//		CraftingHelper.register(snownee.kiwi.recipe.ModuleLoadedCondition.Serializer.INSTANCE);
//		CraftingHelper.register(snownee.kiwi.recipe.TryParseCondition.Serializer.INSTANCE);
//		CraftingHelper.register(this.RL("full_block"), FullBlockIngredient.SERIALIZER);
//		CraftingHelper.register(this.RL("alternatives"), AlternativesIngredientSerializer.INSTANCE);
//	}
}
