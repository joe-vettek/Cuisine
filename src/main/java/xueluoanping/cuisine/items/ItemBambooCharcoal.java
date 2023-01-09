package xueluoanping.cuisine.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.Nullable;

public class ItemBambooCharcoal extends Item {
	public ItemBambooCharcoal(Properties properties) {
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return 40;
	}
}
