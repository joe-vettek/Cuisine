package xueluoanping.cuisine.craft;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
import net.minecraft.util.datafix.fixes.TrappedChestBlockEntityFix;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.Optional;

public class MillingRecipe implements Recipe<RecipeWrapper> {
    private final String group;
    private final Ingredient input;
    private final Ingredient output;
    private final SizedFluidIngredient inputFluid;
    private final SizedFluidIngredient outputFluid;

    public MillingRecipe(String group, Ingredient input, Ingredient output, SizedFluidIngredient inputFluid, SizedFluidIngredient outputFluid) {
        this.group = group;
        this.input = input;
        this.output = output;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
    }

    public MillingRecipe(String group, Ingredient input, Ingredient output, Optional<SizedFluidIngredient> inputFluid, Optional<SizedFluidIngredient> outputFluid) {
        this(group, input, output, inputFluid.orElse(null), outputFluid.orElse(null));
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


    public NonNullList<Ingredient> getIngredientsOutput() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.output);
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
        return RecipeRegister.millingSerializer.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegister.millingType.get();
    }


    public static class Serializer implements RecipeSerializer<MillingRecipe> {

        public static final MapCodec<MillingRecipe> codec = RecordCodecBuilder.mapCodec(
                recipeInstance -> recipeInstance.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                                Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(r -> r.input),
                                Ingredient.CODEC.fieldOf("output").forGetter(r -> r.output),
                                SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("inputFluid").forGetter(r -> java.util.Optional.ofNullable(r.inputFluid)),
                                SizedFluidIngredient.FLAT_CODEC.optionalFieldOf("outputFluid").forGetter(r -> java.util.Optional.ofNullable(r.outputFluid))
                        )
                        .apply(recipeInstance, MillingRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MillingRecipe> streamCodec =
                StreamCodec.of(MillingRecipe.Serializer::toNetwork, MillingRecipe.Serializer::fromNetwork);


        public static MillingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient ingredient2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            var fluidStack = buffer.readBoolean() ? SizedFluidIngredient.STREAM_CODEC.decode(buffer) : null;
            var fluidStack2 = buffer.readBoolean() ? SizedFluidIngredient.STREAM_CODEC.decode(buffer) : null;
            return new MillingRecipe(groupIn, ingredient, ingredient2, fluidStack, fluidStack2);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, MillingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.output);
            buffer.writeBoolean(recipe.inputFluid != null);
            if (recipe.inputFluid != null)
                SizedFluidIngredient.STREAM_CODEC.encode(buffer, recipe.inputFluid);
            buffer.writeBoolean(recipe.outputFluid != null);
            if (recipe.outputFluid != null)
                SizedFluidIngredient.STREAM_CODEC.encode(buffer, recipe.outputFluid);
        }

        @Override
        public MapCodec<MillingRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MillingRecipe> streamCodec() {
            return streamCodec;
        }
    }

}
