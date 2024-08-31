package xueluoanping.cuisine.register;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.craft.ConfigCondition;
import xueluoanping.cuisine.craft.MillingRecipe;

import static xueluoanping.cuisine.Cuisine.MODID;

public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> DRRecipeSerializer =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> DRRecipeType =
            DeferredRegister.create(Registries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, Cuisine.MODID);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigCondition>> CONFIG = CONDITION_CODECS.register("config", () -> ConfigCondition.CODEC);


    public static final DeferredHolder<RecipeSerializer<?>, BasinSqueezingRecipe.Serializer> squeezingSerializer = DRRecipeSerializer
            .register("squeezing", BasinSqueezingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<BasinSqueezingRecipe>> squeezingType =
            DRRecipeType.register("squeezing",  ()->RecipeType.simple(Cuisine.rl("squeezing")));

    public static final DeferredHolder<RecipeSerializer<?>, MillingRecipe.Serializer> millingSerializer = DRRecipeSerializer
            .register("milling", MillingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MillingRecipe>> millingType =
            DRRecipeType.register("milling",  ()->RecipeType.simple(Cuisine.rl("milling")));

}
