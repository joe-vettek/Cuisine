package xueluoanping.cuisine.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import xueluoanping.cuisine.data.recipe.builder.SqueezingRecipeBuilder;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.FluidRegister;

public class SqueezingRecipeGen {
    public static void register(Consumer<FinishedRecipe> consumer) {
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.APPLE), new FluidStack(FluidRegister.juice.get(), 200))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.GLOW_BERRIES), new FluidStack(FluidRegister.juice.get(), 50))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.SUGAR_CANE), new FluidStack(FluidRegister.juice.get(), 300))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.CARROT), new FluidStack(FluidRegister.juice.get(), 250))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.SWEET_BERRIES), new FluidStack(FluidRegister.juice.get(), 50))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.MELON_SLICE), new FluidStack(FluidRegister.juice.get(), 50))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.PUMPKIN), new FluidStack(FluidRegister.juice.get(), 500))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(BlockRegister.bamboo_shoot.get()), new FluidStack(FluidRegister.juice.get(), 250))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.KELP), new FluidStack(FluidRegister.juice.get(), 75))
                .build(consumer);
        SqueezingRecipeBuilder.
                create(Ingredient.of(Items.CHORUS_FRUIT), new FluidStack(FluidRegister.juice.get(), 200))
                .build(consumer);
    }

}
