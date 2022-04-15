package xueluoanping.cuisine.plugin.jei.interpreter;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import xueluoanping.cuisine.api.util.NBTUtils;

public class BasinInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
	public static final BasinInterpreter INSTANCE = new BasinInterpreter();

	private BasinInterpreter() {
	}

	@Override
	public String apply(ItemStack itemStack, UidContext uidContext) {
		if (!itemStack.hasTag() ) {
			return IIngredientSubtypeInterpreter.NONE;
		}
//		if (!itemStack.hasTag() || uidContext==UidContext.Recipe) {
//			return IIngredientSubtypeInterpreter.NONE;
//		}

		String basinType = NBTUtils.getNameFromRetxtureStack(itemStack);
		StringBuilder stringBuilder = new StringBuilder(basinType);
		return stringBuilder.toString();
	}

}
