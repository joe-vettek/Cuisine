package xueluoanping.cuisine.event.type;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

public enum EnumFirePitState implements StringRepresentable {
	NONE("none"),
	WOK("wok"),
	STICKS("sticks"),
	FRYING_PAN("frying_pan");
	private final String name;

	private EnumFirePitState(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public static EnumFirePitState matchWithoutError(ItemStack stack) {
		try {
			return EnumFirePitState.valueOf(stack.getOrCreateTag().getString("Component").toUpperCase(Locale.ROOT));
		} catch (Exception ex) {
			ex.printStackTrace();
			return EnumFirePitState.NONE;
		}
	}

}
