package xueluoanping.cuisine.blockentity.handler;

import java.util.HashMap;
import java.util.Map;


import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import xueluoanping.cuisine.api.FuelHandler;
import xueluoanping.cuisine.api.HeatHandler;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.tag.ForgeTags;


public class FuelHeatHandler implements HeatHandler, FuelHandler {
	public static class FuelInfo {
		public final int level;
		public final int heat;

		public FuelInfo(int level, int heat) {
			this.level = level;
			this.heat = heat;
		}
	}

	public static final Map<Item, FuelInfo> ITEM_FUELS = new HashMap<>();
	public static final Map<TagKey<Item>, FuelInfo> ORE_FUELS = new HashMap<>();

	static {
		ITEM_FUELS.put(Items.BLAZE_ROD, new FuelInfo(3, 1000));
		ITEM_FUELS.put(Items.COAL, new FuelInfo(3, 800));
		ITEM_FUELS.put(BlockRegister.bamboo_item.get(), new FuelInfo(1, 250));

		ORE_FUELS.put(ItemTags.WOOL, new FuelInfo(1, 100));
		ORE_FUELS.put(ItemTags.CARPETS, new FuelInfo(1, 67));

		// ORE_FUELS.put(OreDictDefinition.of("fuelCoke"), new FuelInfo(3, 2000));
		ORE_FUELS.put(ForgeTags.COAL_COKE, new FuelInfo(3,2000));
		ORE_FUELS.put(ItemTags.SAPLINGS, new FuelInfo(1, 100));
		ITEM_FUELS.put(Items.PAPER, new FuelInfo(1, 100));
		// ORE_FUELS.put(OreDictDefinition.of("paper"), new FuelInfo(1, 150));
		ITEM_FUELS.put(Items.SUGAR_CANE, new FuelInfo(1, 100));
		// ORE_FUELS.put(OreDictDefinition.of("sugarcane"), new FuelInfo(1, 100));
	}

	public static FuelInfo registerFuel(Item item, int level, int heat) {
		if (ITEM_FUELS.containsKey(item)) {
			return null;
		}
		return ITEM_FUELS.put(item, new FuelInfo(level, heat));
	}

	public static FuelInfo unregisterFuel(Item item) {
		return ITEM_FUELS.remove(item);
	}

	public static FuelInfo registerFuel(TagKey<Item> ore, int level, int heat) {
		if (ORE_FUELS.containsKey(ore)) {
			return null;
		}
		return ORE_FUELS.put(ore, new FuelInfo(level, heat));
	}

	public static FuelInfo unregisterFuel(TagKey<Item> ore) {
		return ORE_FUELS.remove(ore);
	}

	private float encouragement = 0;
	private float burnTime = 0;
	private float heat, minHeat, maxHeat, heatPower, radiation;

	public FuelHeatHandler() {
		heat = 0;
		minHeat = 0;
		maxHeat = 0;
		heatPower = 0;
		radiation = 0;
	}

	public FuelHeatHandler(float minHeat, float maxHeat, float heatPower, float radiation) {
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
		this.heatPower = heatPower;
		this.radiation = radiation;
	}

	@Override
	public void update(float bonusRate) {
		if (burnTime > 0) {
			burnTime -= (1 + bonusRate) * (1 + encouragement);
			encouragement = Math.max(encouragement - 0.01F, 0);
			burnTime = Mth.clamp(burnTime, 0, getMaxBurnTime());
			heat += getHeatPower();
		}
		heat -= radiation;
		heat = Mth.clamp(heat, minHeat, getMaxHeat());
	}

	@Override
	public float getHeatPower() {
		return getBurnTime() > 0 ? getMaxHeatPower() : 0;
	}

	@Override
	public float getMaxHeatPower() {
		return heatPower;
	}

	public void setHeatPower(float heatPower) {
		this.heatPower = heatPower;
	}

	public float getMinHeat() {
		return minHeat;
	}

	public void setMinHeat(float minHeat) {
		this.minHeat = minHeat;
	}

	public void setMaxHeat(float maxHeat) {
		this.maxHeat = maxHeat;
	}

	public float getRadiation() {
		return radiation;
	}

	public void setRadiation(float radiation) {
		this.radiation = radiation;
	}

	@Override
	public float getHeat() {
		return heat;
	}

	@Override
	public void setHeat(float heat) {
		this.heat = heat;
	}

	@Override
	public float getMaxHeat() {
		return maxHeat;
	}

	@Override
	public void addHeat(float delta) {
		heat = Mth.clamp(heat + delta, 0, getMaxHeat());
	}

	public void encourage() {
		encouragement = Mth.clamp(encouragement + 0.5F, 0, 1);
	}

	@Override
	public float getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(float burnTime) {
		this.burnTime = burnTime;
	}

	public int getLevel() {
		if (burnTime == 0) {
			return 0;
		}
		return ((((int) (burnTime - 1) / 1000) + encouragement) > 0) ? 2 : 1;
	}

	@Override
	public float getMaxBurnTime() {
		return 3000;
	}

	@Override
	public void addBurnTime(float delta) {
		burnTime = Mth.clamp(burnTime + delta, 0, getMaxBurnTime());
	}

	public ItemStack addFuel(ItemStack stack) {
		stack = stack.copy();
		FuelInfo info = getFuel(stack);
		if (info != null) {
			int max = info.level * 1000;
			if (getHeat() + 20 < max) {
				float newBurnTime = Math.min(burnTime + info.heat, max);
				setBurnTime(newBurnTime);
				stack.shrink(1);
			}
		}
		return stack;
	}

	public static boolean isFuel(ItemStack stack, boolean useVanillaFuels) {
		if (stack.isEmpty() || !stack.getItem().getContainerItem(stack).isEmpty()) {
			return false;
		}
		if (useVanillaFuels && stack.getBurnTime(RecipeType.SMELTING) > 0) {
			return true;
		}
		if (ITEM_FUELS.containsKey(stack.getItem())) {
			return true;
		}
		if (ITEM_FUELS.containsKey(stack.getItem())) {
			return true;
		}
		for (TagKey<Item> itemTagKey :
				stack.getItem().builtInRegistryHolder().tags().toList()) {
			if (ORE_FUELS.containsKey(itemTagKey)) {
				return true;
			}
		}
		return false;
	}

	public static FuelInfo getFuel(ItemStack stack) {
		FuelInfo info = ITEM_FUELS.get(stack.getItem());
		// if (info == null)
		// {
		//     info = ITEM_FUELS.get(ItemDefinition.of(stack.getItem(), OreDictionary.WILDCARD_VALUE));
		// }
		if (info == null) {
			for (TagKey<Item> itemTagKey :
					stack.getItem().builtInRegistryHolder().tags().toList()) {
				info = ORE_FUELS.get(itemTagKey);
				if (info != null) {
					break;
				}
			}
		}
		if (info == null) {
			int burnTime = stack.getBurnTime(RecipeType.SMELTING);
			if (burnTime > 0) {
				info = new FuelInfo(2, burnTime);
			}
		}
		return info;
	}

}
