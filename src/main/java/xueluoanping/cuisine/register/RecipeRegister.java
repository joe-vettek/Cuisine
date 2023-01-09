package xueluoanping.cuisine.register;


import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.craft.ConfigCondition;
import xueluoanping.cuisine.craft.MillingRecipe;

import static xueluoanping.cuisine.Cuisine.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registry.RECIPE_TYPE.key(), MODID);


    @SubscribeEvent // ModBus, can't use addListener due to nested genetics.
    public static void registerRecipeSerialziers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);

    }

    public static final RegistryObject<RecipeSerializer<BasinSqueezingRecipe>> squeezingSerializer = DRRecipeSerializer
            .register("squeezing", BasinSqueezingRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<BasinSqueezingRecipe>> squeezingType =
            DRRecipeType.register("squeezing", () -> new RecipeType<BasinSqueezingRecipe>() {
                @Override
                public String toString() {
                    return Cuisine.rl("squeezing").toString();
                }
            });

    public static final RegistryObject<RecipeSerializer<MillingRecipe>> millingSerializer = DRRecipeSerializer
            .register("milling", MillingRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<MillingRecipe>> millingType =
            DRRecipeType.register("milling", () -> new RecipeType<MillingRecipe>() {
                @Override
                public String toString() {
                    return Cuisine.rl("milling").toString();
                }
            });
}
