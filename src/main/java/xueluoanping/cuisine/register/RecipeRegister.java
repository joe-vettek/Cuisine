package xueluoanping.cuisine.register;


import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.api.craft.BasinSqueezingRecipe;

import static xueluoanping.cuisine.Cuisine.MODID;

public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registry.RECIPE_TYPE.key(), MODID);


    public static final RegistryObject<RecipeSerializer<BasinSqueezingRecipe>> squeezingSerializer = DRRecipeSerializer
            .register("squeezing", BasinSqueezingRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<BasinSqueezingRecipe>> squeezingType =
            DRRecipeType.register("squeezing", () -> new RecipeType<BasinSqueezingRecipe>() {
                @Override
                public String toString() {
                    return Cuisine.rl("squeezing").toString();
                }
            });
}
