package xueluoanping.cuisine.data.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.function.Consumer;

public class SqueezingRecipeBuilder {
    private final Ingredient input;
    private final FluidStack result;

    public SqueezingRecipeBuilder(Ingredient input, FluidStack result) {
        this.input = input;
        this.result = result;
    }

    public static SqueezingRecipeBuilder create(Ingredient input, FluidStack result) {
        return new SqueezingRecipeBuilder(input, result);
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        consumerIn.accept(new SqueezingRecipeBuilder.Result(
                Cuisine.rl("squeezing/" +
                        this.input.getItems()[0].getItem().getRegistryName().getPath() + "_to_"
                         + this.result.getFluid().getRegistryName().getPath()+ this.result.getAmount()),
                this.input, this.result));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SqueezingRecipeBuilder.Result(id, this.input, this.result));

    }

    public static class Result implements FinishedRecipe {
        private final Ingredient input;
        private final FluidStack result;
        private final ResourceLocation id;

        public Result(ResourceLocation id, Ingredient input, FluidStack result) {
            this.input = input;
            this.result = result;
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray arrayIngredients = new JsonArray();
            arrayIngredients.add(this.input.toJson());
            json.add("ingredients", arrayIngredients);

            JsonArray arrayFluids = new JsonArray();
            JsonObject fluid = new JsonObject();
            fluid.addProperty("id", this.result.getFluid().getRegistryName().toString());
            fluid.addProperty("amount", this.result.getAmount());
            JsonObject fluids = new JsonObject();
            fluids.add("fluid", fluid);
            arrayFluids.add(fluids);
            json.add("result", arrayFluids);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeRegister.squeezingSerializer.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
