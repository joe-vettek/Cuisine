package xueluoanping.cuisine.event.type;

import java.util.Locale;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
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

	public static float firepit(ItemStack stack, ClientLevel clientWorld, LivingEntity livingEntity, int i) {

		if (!stack.hasTag()) return 0;
		switch (EnumFirePitState.matchWithoutError(stack)) {
			case WOK:
				return 1;
			case STICKS:
				return 2;
			case FRYING_PAN:
				return 3;
			default:
				return 0;
		}
	}
}
