package xueluoanping.cuisine.plugin.jei.interpreter;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class FirePitInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
	public static final FirePitInterpreter INSTANCE = new FirePitInterpreter();

	private FirePitInterpreter() {
	}

	@Override
	public String apply(ItemStack itemStack, UidContext uidContext) {
		if (!itemStack.hasTag() ) {
			return IIngredientSubtypeInterpreter.NONE;
		}
//		if (!itemStack.hasTag() || uidContext==UidContext.Recipe) {
//			return IIngredientSubtypeInterpreter.NONE;
//		}

		String type = itemStack.getOrCreateTag().getString("Component");
		StringBuilder stringBuilder = new StringBuilder(type);
		return stringBuilder.toString();
	}

}
