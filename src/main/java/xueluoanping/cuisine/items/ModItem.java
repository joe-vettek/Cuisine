package xueluoanping.cuisine.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.Nullable;

public class ModItem extends Item {
	public ModItem(Properties properties) {
		super(properties);
	}

	// @Nullable
	// @Override
	// public FoodProperties getFoodProperties() {
	// 	return super.getFoodProperties();
	// }
	//
	// @Nullable
	// @Override
	// public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
	// 	return IForgeItem.super.getFoodProperties(stack, entity);
	// }
}
