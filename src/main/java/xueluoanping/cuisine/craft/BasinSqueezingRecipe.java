package xueluoanping.cuisine.craft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.JsonParseException;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.Optional;

public class BasinSqueezingRecipe implements Recipe<RecipeWrapper> {
    private final String group;
    private final Ingredient input;
    private final FluidStack result;

    public BasinSqueezingRecipe(String group, Ingredient input, FluidStack result) {
        this.group = group;
        this.input = input;
        this.result = result;
    }

    public BasinSqueezingRecipe(String group, Ingredient input, SizedFluidIngredient result) {
        this(group, input, result.getFluids()[0]);
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
    public ItemStack assemble(RecipeWrapper pInput, HolderLookup.Provider pRegistries) {
        return ItemStack.EMPTY;
    }


    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return ItemStack.EMPTY;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.squeezingSerializer.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.squeezingType.get();
    }


    public static class Serializer implements RecipeSerializer<BasinSqueezingRecipe> {

        public static final MapCodec<BasinSqueezingRecipe> codec = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredients").forGetter(r -> r.input),
                                SizedFluidIngredient.FLAT_CODEC.fieldOf("result").forGetter(r -> SizedFluidIngredient.of(r.result))
                        )
                        .apply(recipeInstance, BasinSqueezingRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, BasinSqueezingRecipe> streamCodec =
                StreamCodec.of(BasinSqueezingRecipe.Serializer::toNetwork, BasinSqueezingRecipe.Serializer::fromNetwork);


        public static BasinSqueezingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            var fluidStack = SizedFluidIngredient.STREAM_CODEC.decode(buffer);
            return new BasinSqueezingRecipe(groupIn, ingredient, fluidStack);
        }


        public static void toNetwork(RegistryFriendlyByteBuf buffer, BasinSqueezingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            SizedFluidIngredient.STREAM_CODEC.encode(buffer, SizedFluidIngredient.of(recipe.result));
        }

        @Override
        public MapCodec<BasinSqueezingRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BasinSqueezingRecipe> streamCodec() {
            return streamCodec;
        }
    }
}
