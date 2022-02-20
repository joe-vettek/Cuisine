package xueluoanping.cuisine.api.recipe;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import snownee.kiwi.recipe.FullBlockIngredient;

import java.util.stream.Stream;

public class ReTxtureIngredient extends Ingredient {
	protected ReTxtureIngredient(Stream<? extends Value> p_43907_) {
		super(p_43907_);
	}

	public static class Serializer implements IIngredientSerializer<ReTxtureIngredient> {
		public Serializer() {
		}

		@Override
		public ReTxtureIngredient parse(FriendlyByteBuf buffer) {
			return null;
		}

		@Override
		public ReTxtureIngredient parse(JsonObject json) {
			return null;
		}

		@Override
		public void write(FriendlyByteBuf buffer, ReTxtureIngredient ingredient) {

		}
	}
}
