package xueluoanping.cuisine.api.type;

import net.minecraft.resources.ResourceLocation;
import xueluoanping.cuisine.api.type.BasicAuraType;
import xueluoanping.cuisine.api.type.BasicBasinType;
import xueluoanping.cuisine.api.type.IAuraType;

import java.util.HashMap;
import java.util.Map;

import static xueluoanping.cuisine.Cuisine.MODID;

public class CuisintAPI {
	public static final Map<ResourceLocation, IAuraType> AURA_TYPES = new HashMap<>();
	public static final BasicAuraType TYPE_OTHER = new BasicAuraType(new ResourceLocation(MODID, "other"), null, 0x2fa8a0, Integer.MIN_VALUE).register();

	public static final Map<Integer, BasicBasinType> BASIN_TYPES = new HashMap<>();
	public static final BasicBasinType TYPE_ORIGIN =
			new BasicBasinType("minecraft:oak_planks")
					.register(0);
	public static final BasicBasinType TYPE_2 =
			new BasicBasinType("minecraft:yellow_glazed_terracotta")
					.register(1);
	public static final boolean isBasinTypeAdded = false;

//	public CuisintAPI() {
//
//	}
}
