package xueluoanping.cuisine.craft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.RecipeRegister;

public class MillingRecipe implements Recipe<RecipeWrapper> {
	private final ResourceLocation id;
	private final String group;
	private final Ingredient input;
	private final Ingredient output;
	private final FluidStack inputFluid;
	private final FluidStack outputFluid;


	public MillingRecipe(ResourceLocation resourceLocation, String group, Ingredient input, Ingredient output, FluidStack inputFluid, FluidStack outputFluid) {
		this.id = resourceLocation;
		this.group = group;
		this.input = input;
		this.output = output;
		this.inputFluid = inputFluid;
		this.outputFluid = outputFluid;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public String getGroup() {
		return this.group;
	}


	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.input);
		return nonnulllist;
	}


	public NonNullList<Ingredient> getIngredientsOutput() {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		nonnulllist.add(this.output);
		return nonnulllist;
	}

	public NonNullList<FluidStack> getinputFluid() {
		NonNullList<FluidStack> nonnulllist = NonNullList.create();
		nonnulllist.add(this.inputFluid);
		return nonnulllist;
	}

	public NonNullList<FluidStack> getOutputFluid() {
		NonNullList<FluidStack> nonnulllist = NonNullList.create();
		nonnulllist.add(this.outputFluid);
		return nonnulllist;
	}

	@Override
	public boolean matches(RecipeWrapper wrapper, Level worldIn) {
		if (wrapper.isEmpty())
			return false;
		return input.test(wrapper.getItem(0));
	}


	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return true;
	}

	@Override
	public ItemStack assemble(RecipeWrapper wrapper) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeRegister.millingSerializer.get();
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeRegister.millingType.get();
	}

	private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
		NonNullList<Ingredient> nonnulllist = NonNullList.create();
		for (int i = 0; i < ingredientArray.size(); ++i) {
			Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
			if (!ingredient.isEmpty()) {
				nonnulllist.add(ingredient);
			}
		}
		return nonnulllist;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MillingRecipe> {


		@Override
		public MillingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final String groupIn = GsonHelper.getAsString(json, "group", "");
			final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "input"));
			final NonNullList<Ingredient> inputItemsOut = readIngredients(GsonHelper.getAsJsonArray(json, "output"));
			Cuisine.logger(Cuisine.MODID + "Load Recipe");

			JsonObject jsonObject1=json.get("inputFluid").getAsJsonObject();
			String[] fluidString1 = jsonObject1.get("id").getAsString().split(":");
			int fluidAmount1 = jsonObject1.get("amount").getAsInt();
			FluidStack fluidIn = new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidString1[0], fluidString1[1]))
							, fluidAmount1);

			JsonObject jsonObject2=json.get("outputFluid").getAsJsonObject();
			String[] fluidString2 = jsonObject2.get("id").getAsString().split(":");
			int fluidAmount2 = jsonObject2.get("amount").getAsInt();
			FluidStack fluidOut = new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidString1[0], fluidString1[1]))
					, fluidAmount1);

			//				Cuisine.logger(fluid.getDisplayName());
			return new MillingRecipe(recipeId, groupIn, inputItemsIn.get(0), inputItemsOut.get(0), fluidIn, fluidOut);


		}

		@Nullable
		@Override
		public MillingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			String groupIn = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			Ingredient ingredient2 = Ingredient.fromNetwork(buffer);
			FluidStack fluidStack = buffer.readFluidStack();
			FluidStack fluidStack2 = buffer.readFluidStack();
			Cuisine.logger(Cuisine.MODID + "" + ingredient.toJson().toString());
			return new MillingRecipe(recipeId, groupIn, ingredient, ingredient2, fluidStack, fluidStack2);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, MillingRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.input.toNetwork(buffer);
			recipe.output.toNetwork(buffer);
			buffer.writeFluidStack(recipe.inputFluid);
			buffer.writeFluidStack(recipe.outputFluid);
		}
	}
}
