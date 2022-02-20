package xueluoanping.cuisine.api.recipe;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xueluoanping.cuisine.api.APIModule;
import xueluoanping.cuisine.api.util.JsonHelper;

import javax.annotation.Nullable;

public class ReTextureShapedRecipe extends ShapedRecipe {
	private final Ingredient texture;
	private final boolean matchAll;

	protected ReTextureShapedRecipe(ShapedRecipe orig, Ingredient texture, boolean matchAll) {
		super(orig.getId(), orig.getGroup(), orig.getWidth(), orig.getHeight(), orig.getIngredients(), orig.getResultItem());
		this.texture = texture;
		this.matchAll = matchAll;
	}

//	public ItemStack getRecipeOutput(Item texture) {
//		return RetexturedBlockItem.setTexture(this.getResultItem().copy(), Block.byItem(texture));
//	}

	public ItemStack assemble(CraftingContainer craftMatrix) {
		ItemStack result = super.assemble(craftMatrix);
		Block currentTexture = null;

		for(int i = 0; i < craftMatrix.getContainerSize(); ++i) {
			ItemStack stack = craftMatrix.getItem(i);
			if (!stack.isEmpty() && this.texture.test(stack)) {
				Block block;
				if (stack.getItem() == result.getItem()) {
//					block = RetexturedBlockItem.getTexture(stack);
					block = Block.byItem(stack.getItem());
				} else {
					block = Block.byItem(stack.getItem());
				}

				if (block != Blocks.AIR) {
					if (currentTexture == null) {
						currentTexture = block;
						if (!this.matchAll) {
							break;
						}
					} else if (currentTexture != block) {
						currentTexture = null;
						break;
					}
				}
			}
		}

		return currentTexture != null ? result : result;
	}

	public RecipeSerializer<?> getSerializer() {
		return APIModule.RETEXTURE2;
	}

	public Ingredient getTexture() {
		return this.texture;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ReTextureShapedRecipe> {
		public Serializer() {
		}

		public ReTextureShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			ShapedRecipe recipe = (ShapedRecipe)SHAPED_RECIPE.fromJson(recipeId, json);
			Ingredient texture = CraftingHelper.getIngredient(JsonHelper.getElement(json, "texture"));
			boolean matchAll = false;
			if (json.has("match_all")) {
				matchAll = json.get("match_all").getAsBoolean();
			}

			return new ReTextureShapedRecipe(recipe, texture, matchAll);
		}

		@Nullable
		public ReTextureShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			ShapedRecipe recipe = (ShapedRecipe)SHAPED_RECIPE.fromNetwork(recipeId, buffer);
			return recipe == null ? null : new ReTextureShapedRecipe(recipe, Ingredient.fromNetwork(buffer), buffer.readBoolean());
		}

		public void toNetwork(FriendlyByteBuf buffer, ReTextureShapedRecipe recipe) {
			SHAPED_RECIPE.toNetwork(buffer, recipe);
			recipe.texture.toNetwork(buffer);
			buffer.writeBoolean(recipe.matchAll);
		}
	}
}
