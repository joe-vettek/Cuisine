package xueluoanping.cuisine.plugin.jei.interpreter;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.plugins.vanilla.brewing.PotionSubtypeInterpreter;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import xueluoanping.cuisine.api.util.NBTUtils;

import java.util.List;
import java.util.Random;

public class BasinInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
	public static final BasinInterpreter INSTANCE = new BasinInterpreter();

	private BasinInterpreter() {
	}

	@Override
	public String apply(ItemStack itemStack, UidContext uidContext) {
		if (!itemStack.hasTag()) {
			return IIngredientSubtypeInterpreter.NONE;
		}
		String basinType = NBTUtils.getNameFromRetxtureStack(itemStack);
		StringBuilder stringBuilder = new StringBuilder(basinType);
		return stringBuilder.toString();
	}

}
