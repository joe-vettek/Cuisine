package snownee.cuisine.plugins.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import snownee.cuisine.CuisineConfig;
import snownee.cuisine.api.Form;
import snownee.cuisine.api.process.prefab.MaterialSqueezing;
import snownee.cuisine.fluids.FluidJuice;
import snownee.cuisine.items.ItemIngredient;

import java.util.*;

public class MaterialSqueezingRecipe extends GenericRecipeWrapper<MaterialSqueezing>
{
    private Collection<ItemStack> extraInputs;

    MaterialSqueezingRecipe(MaterialSqueezing recipe, Collection<ItemStack> extraInputs)
    {
        super(recipe);
        this.extraInputs = extraInputs;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<ItemStack> inputs1 = ItemIngredient.getAllValidFormsWithException(recipe.getMaterial(), EnumSet.of(Form.FULL, Form.PASTE, Form.JUICE));
        List<ItemStack> inputs2 = new ArrayList<>(extraInputs);
        inputs2.addAll(inputs1);
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(inputs2));
        ingredients.setOutput(VanillaTypes.FLUID, FluidJuice.make(recipe.getMaterial(), CuisineConfig.GENERAL.juiceSqueezingAmount));
    }

}
