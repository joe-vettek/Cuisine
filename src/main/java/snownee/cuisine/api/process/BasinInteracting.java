package snownee.cuisine.api.process;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface BasinInteracting extends CuisineProcessingRecipe
{

    @Override
    default boolean matches(Object... inputs)
    {
        if (inputs == null || inputs.length != 2 || inputs[0].getClass() != ItemStack.class || inputs[1].getClass() != FluidStack.class)
        {
            return false;
        }
        return matches((ItemStack) inputs[0], (FluidStack) inputs[1]);
    }

    boolean matches(ItemStack item, @Nullable FluidStack fluid);

    FluidStack getOutput(ItemStack item, @Nullable FluidStack fluid);

    static int descendingCompare(BasinInteracting a, BasinInteracting b)
    {
        return 0; // TODO (Snownee): Fix me
    }

    //    List<ItemStack> getInputItems();
    //
    //    List<FluidStack> getInputFluids();
    //
    //    List<FluidStack> getOutputs();

}