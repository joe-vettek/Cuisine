package xueluoanping.cuisine.plugin.jei.interpreter;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraftforge.fluids.FluidStack;

public class JuiceInterpreter implements IIngredientSubtypeInterpreter<FluidStack> {
	public static final JuiceInterpreter INSTANCE = new JuiceInterpreter();

	private JuiceInterpreter() {
	}

	@Override
	public String apply(FluidStack stack, UidContext uidContext) {
		if (!stack.hasTag() ) {
			return IIngredientSubtypeInterpreter.NONE;
		}
//		if (!itemStack.hasTag() || uidContext==UidContext.Recipe) {
//			return IIngredientSubtypeInterpreter.NONE;
//		}

		String type = stack.getOrCreateTag().getString("source");
//		Cuisine.logger(type);
		StringBuilder stringBuilder = new StringBuilder(type);
		return stringBuilder.toString();
	}

}
