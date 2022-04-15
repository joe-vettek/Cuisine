package xueluoanping.cuisine.api.craft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.JsonParseException;

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

public class BasinSqueezingRecipe implements Recipe<RecipeWrapper> {
    private final ResourceLocation id;
    private final String group;
    private final Ingredient input;
    private final FluidStack result;


    public BasinSqueezingRecipe(ResourceLocation resourceLocation, String group, Ingredient input, FluidStack result) {
        this.id = resourceLocation;
        this.group = group;
        this.input = input;
        this.result = result;
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

    public NonNullList<FluidStack> getResult() {
        NonNullList<FluidStack> nonnulllist = NonNullList.create();
        nonnulllist.add(this.result);
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
        return RecipeRegister.squeezingSerializer.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.squeezingType.get();
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

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BasinSqueezingRecipe> {


        @Override
        public BasinSqueezingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String groupIn = GsonHelper.getAsString(json, "group", "");
            final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            Cuisine.logger(Cuisine.MODID + "Load Recipe");
            if (inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for cutting recipe");
            } else if (inputItemsIn.size() > 1) {
                throw new JsonParseException("Too many ingredients for cutting recipe! Please define only one ingredient");
            } else {
                String[] fluidString = json.get("result").getAsJsonArray()
                        .get(0).getAsJsonObject()
                        .get("fluid").getAsJsonObject().get("id").getAsString().split(":");
                int fluidAmount = json.get("result").getAsJsonArray()
                        .get(0).getAsJsonObject()
                        .get("fluid").getAsJsonObject()
                        .get("amount").getAsInt();
                FluidStack fluid =
                        new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidString[0], fluidString[1]))
                                , fluidAmount);
                fluid.getOrCreateTag().putString("source", inputItemsIn.get(0).getItems()[0].getItem().getRegistryName().toString());
//				Cuisine.logger(fluid.getDisplayName());
                return new BasinSqueezingRecipe(recipeId, groupIn, inputItemsIn.get(0), fluid);

            }
        }

        @Nullable
        @Override
        public BasinSqueezingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            FluidStack fluidStack = buffer.readFluidStack();
            Cuisine.logger(Cuisine.MODID + "" + ingredient.toJson().toString());
            return new BasinSqueezingRecipe(recipeId, groupIn, ingredient, fluidStack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BasinSqueezingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            buffer.writeFluidStack(recipe.result);
        }
    }
}
