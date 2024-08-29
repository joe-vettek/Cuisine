package xueluoanping.cuisine.register;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.craft.ConfigCondition;
import xueluoanping.cuisine.craft.MillingRecipe;

import static xueluoanping.cuisine.Cuisine.MODID;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registries.RECIPE_TYPE, MODID);


    @SubscribeEvent // ModBus, can't use addListener due to nested genetics.
    public static void registerRecipeSerialziers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);

    }

    public static final DeferredHolder<RecipeSerializer<?>, BasinSqueezingRecipe.Serializer> squeezingSerializer = DRRecipeSerializer
            .register("squeezing", BasinSqueezingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<BasinSqueezingRecipe>> squeezingType =
            DRRecipeType.register("squeezing", () -> new RecipeType<BasinSqueezingRecipe>() {
                @Override
                public String toString() {
                    return Cuisine.rl("squeezing").toString();
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, MillingRecipe.Serializer> millingSerializer = DRRecipeSerializer
            .register("milling", MillingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MillingRecipe>> millingType =
            DRRecipeType.register("milling", () -> new RecipeType<MillingRecipe>() {
                @Override
                public String toString() {
                    return Cuisine.rl("milling").toString();
                }
            });
}
