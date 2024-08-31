package xueluoanping.cuisine.data.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
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

    public void build(RecipeOutput consumerIn, String save) {
        ResourceLocation saveRes = ResourceLocation.tryParse(save);
        this.build(consumerIn, saveRes);
    }

    public void build(RecipeOutput consumerIn) {
        String s= "%s_to_%s_%s".formatted(BuiltInRegistries.ITEM.getKey(input.getItems()[0].getItem()),BuiltInRegistries.FLUID.getKey(result.getFluid()),result.getAmount());
        ResourceLocation saveRes = Cuisine.rl(s);
        this.build(consumerIn, saveRes);
    }

    public void build(RecipeOutput consumerIn, ResourceLocation id) {
        id = Cuisine.rl(id.getNamespace(), "%s/%s".formatted(RecipeRegister.squeezingType.getId().getPath(), id.getPath()));

        consumerIn.accept(id,new BasinSqueezingRecipe("",this.input, this.result),null);
    }

}
